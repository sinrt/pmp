package com.pmp.nwms.logging.aspect;

import com.blueconic.browscap.BrowsCapField;
import com.blueconic.browscap.Capabilities;
import com.blueconic.browscap.UserAgentParser;
import com.blueconic.browscap.UserAgentService;
import com.pmp.nwms.NwmsApp;
import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.domain.BaseEntity;
import com.pmp.nwms.logging.IgnoreLoggingValue;
import com.pmp.nwms.logging.biz.LogHandler;
import com.pmp.nwms.logging.data.entity.Log;
import com.pmp.nwms.logging.data.entity.LogDetail;
import com.pmp.nwms.logging.data.enums.InputParamType;
import com.pmp.nwms.logging.util.LogUtil;
import com.pmp.nwms.tools.ObjectSizeCalculator;
import com.pmp.nwms.util.GsonUtil;
import com.pmp.nwms.web.ClientInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.zalando.problem.Problem;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;


@Order(2)
@Aspect
public class NwmsLogger {

    private static final Logger logger = LoggerFactory.getLogger(NwmsApp.class);

    @Autowired
    private LogHandler logHandler;
    @Autowired
    private ParameterNameDiscoverer parameterNameDiscoverer;
    @Autowired
    private NwmsConfig nwmsConfig;

    private UserAgentParser parser;

    @PostConstruct
    public void init() {
        try {
            parser = new UserAgentService().loadParser(Arrays.asList(BrowsCapField.BROWSER, BrowsCapField.BROWSER_TYPE,
                BrowsCapField.BROWSER_VERSION,
                BrowsCapField.BROWSER_MAJOR_VERSION,
                BrowsCapField.DEVICE_TYPE, BrowsCapField.PLATFORM, BrowsCapField.PLATFORM_VERSION));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Pointcut("within(@org.springframework.stereotype.Controller *) || within(@org.springframework.web.bind.annotation.RestController *)")
    public void controller() {
    }

    @Pointcut("execution(* *(..))")
    public void methodPointcut() {
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RequestMapping *) || " +
        "within(@org.springframework.web.bind.annotation.PostMapping *) || " +
        "within(@org.springframework.web.bind.annotation.GetMapping *) || " +
        "within(@org.springframework.web.bind.annotation.PutMapping *) || " +
        "within(@org.springframework.web.bind.annotation.PatchMapping *) || " +
        "within(@org.springframework.web.bind.annotation.DeleteMapping *)")
    public void requestMapping() {
    }


    /*@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
        "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
        "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
        "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
        "@annotation(org.springframework.web.bind.annotation.PatchMapping) || " +
        "@annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
        "")*/
    @Around("controller() && methodPointcut() && requestMapping()")
    public Object log(final ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Object result;
        Date callDate = new Date();
        boolean success = true;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            //check : this should never happen.
            result = throwable;
            success = false;
        }
        Date endCallDate = new Date();
        long resultSize;
        try {
            resultSize = ObjectSizeCalculator.sizeOf(result);
        } catch (IllegalAccessException e) {
            resultSize = -1;
        }

        final long responseSize = resultSize;


        final Object finalResult = result;
        final boolean finalSuccess = success;
        final SecurityContext context = SecurityContextHolder.getContext();
        final String path = request.getRequestURI();
        final String requestURI = makeRequestURI(request, joinPoint);
        String userAgent = request.getHeader("User-Agent");
        ClientInfo clientInfo = makeClientInfo(path, request);
        Thread t = new Thread(() -> {
            SecurityContextHolder.setContext(context);
            String methodFullName = makeLogSourceName(joinPoint);
            final Log log = LogUtil.makePlainLogObject(methodFullName, requestURI, clientInfo, nwmsConfig.getAppWebUrl());
            log.setUserAgent(userAgent);
            log.setCallDateTime(callDate);
            log.setEndCallDateTime(endCallDate);
            log.setInputLogDetails(findLoggableObjects(joinPoint));
            if (log.getInputLogDetails() != null && !log.getInputLogDetails().isEmpty() && log.getUserName().equalsIgnoreCase("Anonymous")) {
                for (LogDetail logDetail : log.getInputLogDetails()) {
                    if (logDetail.getParamName().equalsIgnoreCase("LoginVM.loginVM")) {
                        log.setUserName(((JSONObject) logDetail.getParamValue()).get("username").toString());
                    }
                }
            }
            if (!isIgnoreResultValue(getJoinPointMethod(joinPoint))) {
                if (finalResult instanceof ResponseEntity) {
                    Object responseBody = ((ResponseEntity) finalResult).getBody();
                    if (responseBody instanceof BaseEntity) {
                        log.setResponse(responseBody.getClass().getName() + ":" + ((BaseEntity) responseBody).fetchPrimaryKey());
                    } else if (responseBody instanceof Collection) {
                        if (!((Collection) responseBody).isEmpty() && ((Collection) responseBody).stream().anyMatch(BaseEntity.class::isInstance)) {
                            List<String> items = new ArrayList<>();
                            for (Object o : ((Collection) responseBody)) {
                                items.add(o.getClass().getName() + ":" + ((BaseEntity) o).fetchPrimaryKey());
                            }
                            log.setResponse(items);
                        } else {
                            log.setResponse(responseBody);
                        }
                    } else if (responseBody instanceof Map) {
                        if (!((Map) responseBody).isEmpty() && ((Map) responseBody).values().stream().anyMatch(BaseEntity.class::isInstance)) {
                            Map<Object, String> items = new LinkedHashMap<>();
                            for (Object o : ((Map) responseBody).entrySet()) {
                                Map.Entry entry = (Map.Entry) o;
                                items.put(entry.getKey(), entry.getValue().getClass().getName() + ":" + ((BaseEntity) entry.getValue()).fetchPrimaryKey());
                            }
                            log.setResponse(items);
                        } else {
                            log.setResponse(responseBody);
                        }
                    } else {
                        log.setResponse(responseBody);
                    }
                } else if (finalResult instanceof Throwable) {
                    log.setResponse(finalResult.getClass().getName());//todo is this enough or should I create a Problem object and put it here like the one that the ExceptionTranslator is creating?
                } else {
                    log.setResponse(finalResult);
                }
            } else {
                log.setResponse(null);
            }
            log.setDuration(endCallDate.getTime() - callDate.getTime());
            log.setResponseSize(responseSize);
            log.setSuccess(finalSuccess);
            logHandler.addLogToQueue(log);
        });

        t.start();

        if (result instanceof Throwable) {
            throw (Throwable) result;
        } else {
            return result;
        }

    }

    @Pointcut("execution(* com.pmp.nwms.web.rest.errors.ExceptionTranslator.handleMethodArgumentNotValid(..))")
    public void validationErrorPointcut() {
    }


    @Around("validationErrorPointcut() && args(ex, request)")
    public Object logValidation(final ProceedingJoinPoint joinPoint, final MethodArgumentNotValidException ex, final NativeWebRequest request) throws Throwable {
        Date callDate = new Date();
        ResponseEntity<Problem> result = (ResponseEntity<Problem>) joinPoint.proceed();
        Date endCallDate = new Date();
        long resultSize;
        try {
            resultSize = ObjectSizeCalculator.sizeOf(result);
        } catch (IllegalAccessException e) {
            resultSize = -1;
        }
        final long responseSize = resultSize;
        final SecurityContext context = SecurityContextHolder.getContext();
        HttpServletRequest nativeRequest = (HttpServletRequest) request.getNativeRequest();
        final String path = nativeRequest.getRequestURI();
        final String requestURI = makeRequestURI(nativeRequest, joinPoint);
        String userAgent = request.getHeader("User-Agent");
        ClientInfo clientInfo = makeClientInfo(path, nativeRequest);

        Thread t = new Thread(() -> {
            SecurityContextHolder.setContext(context);
            String methodFullName = ex.getParameter().getContainingClass().getName() + "." + ex.getParameter().getMethod().getName();
            final Log log = LogUtil.makePlainLogObject(methodFullName, requestURI, clientInfo, nwmsConfig.getAppWebUrl());
            log.setUserAgent(userAgent);
            log.setCallDateTime(callDate);
            log.setEndCallDateTime(endCallDate);
            log.setInputLogDetails(findValidationLoggableObjects(ex.getBindingResult().getModel()));
            if (log.getInputLogDetails() != null && !log.getInputLogDetails().isEmpty() && log.getUserName().equalsIgnoreCase("Anonymous")) {
                for (LogDetail logDetail : log.getInputLogDetails()) {
                    if (logDetail.getParamName().equalsIgnoreCase("loginVM")) {
                        log.setUserName(((JSONObject) logDetail.getParamValue()).get("username").toString());
                    }
                }
            }
            log.setResponse(makeValidationResponse(result));
            log.setDuration(endCallDate.getTime() - callDate.getTime());
            log.setResponseSize(responseSize);
            log.setSuccess(false);
            logHandler.addLogToQueue(log);
        });

        t.start();

        return result;
    }

    @Pointcut("execution(* handleAuthentication(..))")
    public void authenticationErrorPointcut() {
    }

    @Around("authenticationErrorPointcut() && args(ex, request)")
    public Object logAuthentication(final ProceedingJoinPoint joinPoint, final AuthenticationException ex, final NativeWebRequest request) throws Throwable {
        Date callDate = new Date();
        ResponseEntity<Problem> result = (ResponseEntity<Problem>) joinPoint.proceed();
        Date endCallDate = new Date();
        long resultSize;
        try {
            resultSize = ObjectSizeCalculator.sizeOf(result);
        } catch (IllegalAccessException e) {
            resultSize = -1;
        }
        final long responseSize = resultSize;
        final SecurityContext context = SecurityContextHolder.getContext();
        HttpServletRequest nativeRequest = (HttpServletRequest) request.getNativeRequest();
        final String path = nativeRequest.getRequestURI();
        final String requestURI = makeRequestURI(nativeRequest, joinPoint);
        String userAgent = request.getHeader("User-Agent");
        ClientInfo clientInfo = makeClientInfo(path, nativeRequest);

        Thread t = new Thread(() -> {
            SecurityContextHolder.setContext(context);
            final Log log = LogUtil.makePlainLogObject(requestURI, requestURI, clientInfo, nwmsConfig.getAppWebUrl());
            log.setUserAgent(userAgent);
            log.setCallDateTime(callDate);
            log.setEndCallDateTime(endCallDate);
            log.setResponse(makeValidationResponse(result));
            log.setDuration(endCallDate.getTime() - callDate.getTime());
            log.setResponseSize(responseSize);
            log.setSuccess(false);
            logHandler.addLogToQueue(log);
        });

        t.start();

        return result;

    }

    private JSONObject makeValidationResponse(ResponseEntity<Problem> result) {
        String json = GsonUtil.toJson(result.getBody());
        JSONObject jsonObject = new JSONObject(json);
        jsonObject.remove("stackTrace");
        return jsonObject;
    }

    private List<LogDetail> findValidationLoggableObjects(Map<String, Object> model) {
        ArrayList<LogDetail> logDetails = new ArrayList<>();
        if (model != null && !model.isEmpty()) {
            for (Map.Entry<String, Object> entry : model.entrySet()) {
                String key = entry.getKey();
                Object arg = entry.getValue();
                if (!(arg instanceof BeanPropertyBindingResult)) {
                    LogDetail detail = new LogDetail();
                    detail.setParamType(InputParamType.ValidationParameter);
                    detail.setParamName(key);
                    if (ClassUtils.isPrimitiveOrWrapper(arg.getClass())) {
                        detail.setParamValue(arg);
                    } else {
                        String json = GsonUtil.toJson(arg);
                        JSONObject jsonObject = new JSONObject(json);
                        detail.setParamValue(jsonObject);
                    }
                    logDetails.add(detail);
                }
            }
        }
        return logDetails;
    }

    private String makeRequestURI(HttpServletRequest request, ProceedingJoinPoint joinPoint) {
        String requestURI = request.getRequestURI();
        if (StringUtils.isEmpty(requestURI)) {
            requestURI = request.getServletPath();
        }
        if (StringUtils.isEmpty(requestURI)) {
            requestURI = "unknown";
        }
        Object[] args = joinPoint.getArgs();
        if (ArrayUtils.isNotEmpty(args)) {
            Method method = getJoinPointMethod(joinPoint);
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            int argNumber = 0;
            for (Object arg : args) {
                if (ObjectUtils.anyNotNull(arg)) {
                    InputParamType argParamType = findArgParamType(parameterAnnotations, argNumber);
                    if (InputParamType.PathVariable.equals(argParamType)) {
                        requestURI = requestURI.replace("/" + arg, "");
                    }
                }
            }
        }
        return requestURI;
    }


    private String makeLogSourceName(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        return signature.getDeclaringTypeName() + "." + signature.getName();
    }


    private List<LogDetail> findLoggableObjects(JoinPoint joinPoint) {
        List<LogDetail> result = new ArrayList<>();

        Object[] args = joinPoint.getArgs();
        if (org.apache.commons.lang3.ArrayUtils.isNotEmpty(args)) {
            Method method = getJoinPointMethod(joinPoint);

            Annotation[][] parameterAnnotations = method.getParameterAnnotations();

            String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
            int argNumber = 0;
            for (Object arg : args) {
                if (ObjectUtils.anyNotNull(arg)) {
                    LogDetail detail = new LogDetail();

                    if (arg instanceof Pageable) {
                        detail.setParamType(InputParamType.PageableParameter);
                        detail.setParamName("pageable");
                        String json = GsonUtil.toJson(arg);
                        JSONObject jsonObject = new JSONObject(json);
                        detail.setParamValue(jsonObject);
                        result.add(detail);
                    } else {
                        InputParamType argParamType = findArgParamType(parameterAnnotations, argNumber);
                        if (ObjectUtils.anyNotNull(argParamType)) {
                            detail.setParamType(argParamType);
                            detail.setParamName(findArgParamName(arg, parameterAnnotations, parameterNames, argNumber));
                            if (!isIgnoreParamValue(parameterAnnotations, argNumber)) {
                                if (ClassUtils.isPrimitiveOrWrapper(arg.getClass()) || arg instanceof String) {
                                    detail.setParamValue(arg);
                                } else {
                                    String json = GsonUtil.toJson(arg);
                                    JSONObject jsonObject = new JSONObject(json);
                                    detail.setParamValue(jsonObject);
                                }
                            } else {
                                detail.setParamValue("ignored-param-value");
                            }
                            result.add(detail);
                        }
                    }
                }
                argNumber++;
            }
        }

        return result;
    }

    private String findArgParamName(Object arg, Annotation[][] parameterAnnotations, String[] parameterNames, int argNumber) {
        if (ObjectUtils.anyNotNull(parameterAnnotations) && ObjectUtils.anyNotNull(parameterAnnotations[argNumber])) {
            Annotation[] parameterAnnotation = parameterAnnotations[argNumber];
            for (Annotation annotation : parameterAnnotation) {
                if (annotation.annotationType().equals(RequestHeader.class)) {
                    return ((RequestHeader) annotation).value();
                } else if (annotation.annotationType().equals(PathVariable.class)) {
                    return ((PathVariable) annotation).value();
                } else if (annotation.annotationType().equals(RequestBody.class)) {
                    return arg.getClass().getSimpleName() + "." + parameterNames[argNumber];
                } else if (annotation.annotationType().equals(RequestParam.class)) {
                    return ((RequestParam) annotation).value();
                }
            }
        }
        return null;
    }

    private boolean isIgnoreParamValue(Annotation[][] parameterAnnotations, int argNumber) {
        if (ObjectUtils.anyNotNull(parameterAnnotations) && ObjectUtils.anyNotNull(parameterAnnotations[argNumber])) {
            Annotation[] parameterAnnotation = parameterAnnotations[argNumber];
            for (Annotation annotation : parameterAnnotation) {
                if (annotation.annotationType().equals(IgnoreLoggingValue.class)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isIgnoreResultValue(Method method) {
        return method.isAnnotationPresent(IgnoreLoggingValue.class);
    }

    private InputParamType findArgParamType(Annotation[][] parameterAnnotations, int argNumber) {
        if (ObjectUtils.anyNotNull(parameterAnnotations) && ObjectUtils.anyNotNull(parameterAnnotations[argNumber])) {
            Annotation[] parameterAnnotation = parameterAnnotations[argNumber];
            for (Annotation annotation : parameterAnnotation) {
                if (annotation.annotationType().equals(RequestHeader.class)) {
                    return InputParamType.RequestHeader;
                } else if (annotation.annotationType().equals(PathVariable.class)) {
                    return InputParamType.PathVariable;
                } else if (annotation.annotationType().equals(RequestBody.class)) {
                    return InputParamType.RequestBody;
                } else if (annotation.annotationType().equals(RequestParam.class)) {
                    return InputParamType.RequestParam;
                }
            }
        }
        return null;
    }

    private Method getJoinPointMethod(JoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod();
    }

    private ClientInfo makeClientInfo(String path, HttpServletRequest request) {
        logger.info("make session info for " + path);
        try {
            request.getRemoteAddr();
        } catch (Exception e) {
            logger.warn("error in request " + path);
            return ClientInfo.UNKNOWN;
        }
        try {
            ClientInfo clientInfo = new ClientInfo();
            String userAgent = request.getHeader("User-Agent");
            final Capabilities capabilities = parser.parse(userAgent);
            final String clientIpAddr = getClientIpAddressIfServletRequestExist(request);
            clientInfo.setIp(clientIpAddr);
            clientInfo.setBrowserName(capabilities.getBrowser());
            clientInfo.setBrowserType(capabilities.getBrowserType());
            clientInfo.setBrowserVersion(capabilities.getValue(BrowsCapField.BROWSER_VERSION));
            clientInfo.setBrowserMajorVersion(capabilities.getBrowserMajorVersion());
            clientInfo.setDeviceType(capabilities.getDeviceType());
            clientInfo.setPlatformName(capabilities.getPlatform());
            clientInfo.setPlatformVersion(capabilities.getPlatformVersion());
            return clientInfo;
        } catch (Exception e) {
            logger.warn("after error in request " + path);
            return ClientInfo.UNKNOWN;
        }
    }

    private String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getClientIpAddressIfServletRequestExist(HttpServletRequest request) {

        if (request == null) {
            return ClientInfo.UNKNOWN.getIp();
        }

//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        for (String header : IP_HEADER_CANDIDATES) {
            String ipList = request.getHeader(header);
            if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
                String ip = ipList.split("[,]")[0];
                System.out.println("\n\n\n");
                System.out.println("ip is " + ip + " and found by header " + header);
                System.out.println("\n\n\n");
                return ip;
            }
        }
        System.out.println("\n\n\n");
        System.out.println("ip is " + request.getRemoteAddr() + " and found request.getRemoteAddr()");
        System.out.println("\n\n\n");

        return request.getRemoteAddr();
    }

    private static final String[] IP_HEADER_CANDIDATES = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR",
        "HTTP_X_FORWARDED",
        "HTTP_X_CLUSTER_CLIENT_IP",
        "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED",
        "HTTP_VIA",
        "REMOTE_ADDR",
        "X-Real-IP"
    };

}
