package com.pmp.nwms.service.impl;

import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.domain.ClassroomSurvey;
import com.pmp.nwms.domain.ClassroomSurveyAnswer;
import com.pmp.nwms.domain.ClassroomSurveyOption;
import com.pmp.nwms.domain.enums.SurveyStatus;
import com.pmp.nwms.domain.enums.SurveyType;
import com.pmp.nwms.repository.*;
import com.pmp.nwms.security.NwmsUser;
import com.pmp.nwms.service.ClassroomSurveyService;
import com.pmp.nwms.service.dto.ClassroomSurveyAnswerReportDto;
import com.pmp.nwms.service.dto.ClassroomSurveyDTO;
import com.pmp.nwms.service.dto.ClassroomSurveyOptionDTO;
import com.pmp.nwms.service.listmodel.ClassroomSurveyAnswerListModel;
import com.pmp.nwms.service.listmodel.ClassroomSurveyListModel;
import com.pmp.nwms.util.ClassroomUtil;
import com.pmp.nwms.util.UserUtil;
import com.pmp.nwms.web.rest.errors.*;
import com.pmp.nwms.web.rest.vm.ClassroomSurveyAnswerVM;
import com.pmp.nwms.web.rest.vm.ClassroomSurveyAnsweringVM;
import com.pmp.nwms.web.rest.vm.ClassroomSurveyOptionVM;
import com.pmp.nwms.web.rest.vm.ClassroomSurveyVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("classroomSurveyService")
public class ClassroomSurveyServiceImpl implements ClassroomSurveyService {

    @Autowired
    private ClassroomSurveyRepository classroomSurveyRepository;
    @Autowired
    private ClassroomSurveyOptionRepository classroomSurveyOptionRepository;
    @Autowired
    private ClassroomSurveyAnswerRepository classroomSurveyAnswerRepository;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private ClassroomStudentRepository classroomStudentRepository;
    @Autowired
    private NwmsConfig nwmsConfig;


    @Override
    public Long saveClassroomSurvey(ClassroomSurveyVM vm) {
        validateClassroomSurveyVM(vm);
        ClassroomUtil.checkClassroomModerator(vm.getClassroomId(), classroomRepository, classroomStudentRepository);
        ClassroomSurvey classroomSurvey;
        boolean isNew = true;
        if (vm.getId() == null) {
            classroomSurvey = new ClassroomSurvey();
            classroomSurvey.setClassroom(classroomRepository.getOne(vm.getClassroomId()));
            classroomSurvey.setStatus(SurveyStatus.Defining);
        } else {
            isNew = false;
            Optional<ClassroomSurvey> byId = classroomSurveyRepository.findById(vm.getId());
            if (!byId.isPresent()) {
                throw new EntityNotFountByIdException("ClassroomSurvey", vm.getId());
            }
            classroomSurvey = byId.get();
            if (!vm.getClassroomId().equals(classroomSurvey.getClassroom().getId())) {
                throw new UnableToChangeFieldValueException("ClassroomSurvey", "classroom");
            }
            if (!classroomSurvey.getStatus().equals(SurveyStatus.Defining)) {
                throw new EntityCanNotBeEditedException("ClassroomSurvey", vm.getId());
            }
        }

        classroomSurvey.setQuestion(vm.getQuestion());
        classroomSurvey.setSurveyType(vm.getSurveyType());
        classroomSurvey = classroomSurveyRepository.save(classroomSurvey);

        if (vm.getSurveyType().equals(SurveyType.OnlyDescription) && !isNew) {
            classroomSurveyOptionRepository.deleteByClassroomSurveyId(classroomSurvey.getId());
        } else {
            List<Long> savedOptionIds = new ArrayList<>();
            for (ClassroomSurveyOptionVM optionVm : vm.getOptions()) {
                ClassroomSurveyOption option;
                if (optionVm.getId() == null) {
                    option = new ClassroomSurveyOption();
                } else {
                    Optional<ClassroomSurveyOption> byId = classroomSurveyOptionRepository.findById(optionVm.getId());
                    if (!byId.isPresent()) {
                        throw new EntityNotFountByIdException("ClassroomSurveyOption", optionVm.getId());
                    }
                    option = byId.get();
                    if (!option.getClassroomSurvey().getId().equals(classroomSurvey.getId())) {
                        throw new UnableToChangeFieldValueException("ClassroomSurveyOption", "classroomSurvey");
                    }
                }
                option.setOptionText(optionVm.getOptionText());
                option.setOptionOrder(optionVm.getOptionOrder());
                option.setClassroomSurvey(classroomSurvey);
                option = classroomSurveyOptionRepository.save(option);
                savedOptionIds.add(option.getId());
            }
            if (!isNew) {
                classroomSurveyOptionRepository.deleteByClassroomSurveyIdAndNotIds(classroomSurvey.getId(), savedOptionIds);
            }
        }
        return classroomSurvey.getId();
    }

    private void validateClassroomSurveyVM(ClassroomSurveyVM vm) {
        if (vm.getSurveyType().equals(SurveyType.MultipleOptions) || vm.getSurveyType().equals(SurveyType.Both)) {
            if (vm.getOptions() == null || vm.getOptions().isEmpty()) {
                throw new ClassroomSurveyOptionRequiredException();
            } else if (vm.getOptions().size() < nwmsConfig.getClassroomSurveyOptionMinCount()) {
                throw new ClassroomSurveyOptionMinCountRequiredException(nwmsConfig.getClassroomSurveyOptionMinCount(), vm.getOptions().size());
            } else if (vm.getOptions().size() > nwmsConfig.getClassroomSurveyOptionMaxCount()) {
                throw new ClassroomSurveyOptionMaxCountRequiredException(nwmsConfig.getClassroomSurveyOptionMaxCount(), vm.getOptions().size());
            }

            List<Integer> orders = new ArrayList<>();
            List<String> optionTexts = new ArrayList<>();
            for (ClassroomSurveyOptionVM option : vm.getOptions()) {
                if (orders.contains(option.getOptionOrder())) {
                    throw new DuplicatedClassroomSurveyOptionOrderException(option.getOptionOrder());
                }
                orders.add(option.getOptionOrder());
                if (optionTexts.contains(option.getOptionText())) {
                    throw new DuplicatedClassroomSurveyOptionTextException(option.getOptionText());
                }
                optionTexts.add(option.getOptionText());
            }

        } else if (vm.getOptions() != null && !vm.getOptions().isEmpty()) {
            throw new InvalidClassroomSurveyOptionException();
        }
    }

    @Override
    public ClassroomSurveyDTO getClassroomSurveyVM(Long classroomSurveyId) {
        ClassroomSurvey classroomSurvey = getAndValidateClassroomSurvey(classroomSurveyId);

        ClassroomSurveyDTO dto = new ClassroomSurveyDTO();
        dto.setId(classroomSurvey.getId());
        dto.setClassroomId(classroomSurvey.getClassroom().getId());
        dto.setQuestion(classroomSurvey.getQuestion());
        dto.setSurveyType(classroomSurvey.getSurveyType());
        dto.setStatus(classroomSurvey.getStatus());
        if (classroomSurvey.getSurveyType().equals(SurveyType.MultipleOptions) || classroomSurvey.getSurveyType().equals(SurveyType.Both)) {
            dto.setOptions(classroomSurveyOptionRepository.findClassroomSurveyOptionDTOsUsingClassroomSurveyId(classroomSurveyId));
        }
        return dto;
    }

    private ClassroomSurvey getAndValidateClassroomSurvey(Long classroomSurveyId) {
        Optional<ClassroomSurvey> byId = classroomSurveyRepository.findById(classroomSurveyId);
        if (!byId.isPresent()) {
            throw new EntityNotFountByIdException("ClassroomSurvey", classroomSurveyId);
        }
        ClassroomSurvey classroomSurvey = byId.get();
        if (classroomSurvey.getStatus().equals(SurveyStatus.Deleted)) {
            throw new EntityNotFountByIdException("ClassroomSurvey", classroomSurveyId);
        }
        ClassroomUtil.checkClassroomModerator(classroomSurvey.getClassroom().getId(), classroomRepository, classroomStudentRepository);
        return classroomSurvey;
    }

    @Override
    public void deleteClassroomSurvey(Long classroomSurveyId) {
        ClassroomSurvey classroomSurvey = getAndValidateClassroomSurvey(classroomSurveyId);
        if (!classroomSurvey.getStatus().equals(SurveyStatus.Defining)) {
            throw new EntityCanNotBeDeletedException("ClassroomSurvey", classroomSurveyId, "status");
        }
        classroomSurvey.setStatus(SurveyStatus.Deleted);
        classroomSurvey = classroomSurveyRepository.save(classroomSurvey);
    }

    @Override
    public void changeStatusClassroomSurvey(Long classroomSurveyId, SurveyStatus newStatus, List<SurveyStatus> expectedStatuses) {
        ClassroomSurvey classroomSurvey = getAndValidateClassroomSurvey(classroomSurveyId);
        if (expectedStatuses != null && !expectedStatuses.contains(classroomSurvey.getStatus())) {
            throw new InvalidClassroomSurveyStatusException(classroomSurvey.getStatus(), expectedStatuses);
        }

        classroomSurvey.setStatus(newStatus);
        classroomSurvey = classroomSurveyRepository.save(classroomSurvey);
    }

    @Override
    public int publishClassroomSurveys(Long classroomId) {
        ClassroomUtil.checkClassroomModerator(classroomId, classroomRepository, classroomStudentRepository);
        List<ClassroomSurvey> surveys = classroomSurveyRepository.findAllByClassroomIdAndStatus(classroomId, SurveyStatus.Activated);
        int count = 0;
        if (surveys != null && !surveys.isEmpty()) {
            count = surveys.size();
            for (ClassroomSurvey survey : surveys) {
                survey.setStatus(SurveyStatus.Published);
            }
            classroomSurveyRepository.saveAll(surveys);
        }
        return count;
    }

    @Override
    public int unpublishClassroomSurveys(Long classroomId) {
        ClassroomUtil.checkClassroomModerator(classroomId, classroomRepository, classroomStudentRepository);
        List<ClassroomSurvey> surveys = classroomSurveyRepository.findAllByClassroomIdAndStatus(classroomId, SurveyStatus.Published);
        int count = 0;
        if (surveys != null && !surveys.isEmpty()) {
            count = surveys.size();
            for (ClassroomSurvey survey : surveys) {
                survey.setStatus(SurveyStatus.Finalized);
            }
            classroomSurveyRepository.saveAll(surveys);
        }
        return count;
    }

    @Override
    public List<ClassroomSurveyListModel> getClassroomSurveys(Long classroomId) {
        ClassroomUtil.checkClassroomModerator(classroomId, classroomRepository, classroomStudentRepository);
        return classroomSurveyRepository.getClassroomSurveyListModelsNotInStatuses(classroomId, Collections.singletonList(SurveyStatus.Deleted));
    }

    @Override
    public List<ClassroomSurveyDTO> getClassroomPublishedSurveys(Long classroomId) {
        List<ClassroomSurveyListModel> listModels = classroomSurveyRepository.getClassroomSurveyListModelsInStatuses(classroomId, Collections.singletonList(SurveyStatus.Published));
        List<ClassroomSurveyDTO> result = new ArrayList<>();
        if (listModels != null && !listModels.isEmpty()) {
            List<Long> surveyIds = new ArrayList<>();
            for (ClassroomSurveyListModel listModel : listModels) {
                surveyIds.add(listModel.getId());
            }
            List<ClassroomSurveyOptionDTO> allOptions = classroomSurveyOptionRepository.findClassroomSurveyOptionDTOsUsingClassroomSurveyIds(surveyIds);
            for (ClassroomSurveyListModel listModel : listModels) {
                ClassroomSurveyDTO dto = new ClassroomSurveyDTO();
                dto.setId(listModel.getId());
                dto.setClassroomId(classroomId);
                dto.setQuestion(listModel.getQuestion());
                dto.setSurveyType(listModel.getSurveyType());
                dto.setStatus(listModel.getStatus());
                dto.setOptions(makeSurveyOptions(listModel.getId(), allOptions));
                result.add(dto);
            }
        }
        return result;
    }

    @Override
    public void answerClassroomSurveys(ClassroomSurveyAnsweringVM vm) {
        NwmsUser currentUser = UserUtil.getCurrentUser();
        Long userId = null;
        if (currentUser != null) {
            userId = currentUser.getId();
        } else if (vm.getToken() != null && !vm.getToken().isEmpty()) {
            userId = findUserIdByToken(vm.getToken());
        }
        Map<Long, ClassroomSurvey> classroomSurveys = validateClassroomSurveyAnsweringVM(vm, userId);

        Date now = new Date();
        for (ClassroomSurveyAnswerVM answerVM : vm.getAnswers()) {
            ClassroomSurveyAnswer answer = new ClassroomSurveyAnswer();
            answer.setAnswerText(answerVM.getAnswerText());
            answer.setUserId(userId);
            answer.setClientId(vm.getClientId());
            answer.setToken(vm.getToken());
            answer.setAnswerDateTime(now);
            ClassroomSurvey classroomSurvey = classroomSurveys.get(answerVM.getClassroomSurveyId());
            answer.setClassroomSurvey(classroomSurvey);
            if (answerVM.getSelectedOptionId() != null) {
                answer.setSelectedOption(classroomSurveyOptionRepository.getOne(answerVM.getSelectedOptionId()));
            }
            answer = classroomSurveyAnswerRepository.save(answer);
        }
    }

    @Override
    public List<ClassroomSurveyAnswerListModel> getClassroomSurveyFullAnswerList(Long classroomSurveyId) {
        return classroomSurveyAnswerRepository.getClassroomSurveyAnswerListModels(classroomSurveyId);
    }

    @Override
    public List<ClassroomSurveyAnswerReportDto> getClassroomSurveyAnswerReport(Long classroomSurveyId) {
        ClassroomSurvey classroomSurvey = getAndValidateClassroomSurvey(classroomSurveyId);
        if(classroomSurvey.getSurveyType().equals(SurveyType.OnlyDescription)){
            throw new ClassroomSurveyTypeNotSupportedException();
        }
        return classroomSurveyAnswerRepository.getClassroomSurveyAnswerReportDto(classroomSurveyId);
    }

    private Long findUserIdByToken(String token) {
        return classroomStudentRepository.findStudentIdUsingToken(token);
    }

    private Map<Long, ClassroomSurvey> validateClassroomSurveyAnsweringVM(ClassroomSurveyAnsweringVM vm, Long userId) {
        if (userId == null && vm.getToken() != null && !vm.getToken().isEmpty()) {
            throw new InvalidClassroomTokenException();
        }
        Map<Long, ClassroomSurvey> map = new HashMap<>();
        Long classroomId = null;
        List<Long> classroomSurveyIds = new ArrayList<>();
        for (ClassroomSurveyAnswerVM answer : vm.getAnswers()) {
            classroomSurveyIds.add(answer.getClassroomSurveyId());
        }

        long count = classroomSurveyAnswerRepository.countUsingClientIdAndClassroomSurveyIds(vm.getClientId(), classroomSurveyIds);

        if (count > 0) {
            throw new ClassroomSurveyCanNotBeReansweredException();
        }

        if (userId != null) {
            count = classroomSurveyAnswerRepository.countUsingUserIdAndClassroomSurveyIds(userId, classroomSurveyIds);

            if (count > 0) {
                throw new ClassroomSurveyCanNotBeReansweredException();
            }
        }


        for (ClassroomSurveyAnswerVM answer : vm.getAnswers()) {
            Optional<ClassroomSurvey> byId = classroomSurveyRepository.findById(answer.getClassroomSurveyId());
            if (!byId.isPresent()) {
                throw new EntityNotFountByIdException("ClassroomSurvey", answer.getClassroomSurveyId());
            }
            ClassroomSurvey classroomSurvey = byId.get();
            map.put(answer.getClassroomSurveyId(), classroomSurvey);
            if (classroomId == null) {
                classroomId = classroomSurvey.getClassroom().getId();
                if(vm.getToken() != null && !vm.getToken().trim().isEmpty()){
                    count = classroomStudentRepository.countByTokenAndClassroomAndStudent(vm.getToken(), classroomId, userId);
                    if(count == 0){
                        throw new InvalidClassroomTokenException();
                    }
                }
            } else {
                if (!classroomId.equals(classroomSurvey.getClassroom().getId())) {
                    throw new ClassroomSurveysMustBelongToSameClassroomException();
                }
            }
            if (!classroomSurvey.getStatus().equals(SurveyStatus.Published)) {
                throw new ClassroomSurveyCanNotBeAnsweredException();
            }
            if (classroomSurvey.getSurveyType().equals(SurveyType.OnlyDescription)) {
                if (answer.getAnswerText() == null || answer.getAnswerText().trim().isEmpty()) {
                    throw new ClassroomSurveyAnswerTextCanNotBeEmptyException();
                }
            } else {
                if (answer.getSelectedOptionId() == null) {
                    throw new ClassroomSurveyAnswerOptionMustBeSelectedException();
                }else{
                    long optionCount = classroomSurveyOptionRepository.countByIdAndClassroomSurveyId(answer.getSelectedOptionId(), answer.getClassroomSurveyId());
                    if(optionCount == 0){
                        throw new ClassroomSurveyAnswerOptionMustBelongToSurveyException();
                    }
                }
            }


        }
        return map;
    }

    private List<ClassroomSurveyOptionDTO> makeSurveyOptions(Long classroomSurveyId, List<ClassroomSurveyOptionDTO> allOptions) {
        ArrayList<ClassroomSurveyOptionDTO> options = new ArrayList<>();
        for (ClassroomSurveyOptionDTO option : allOptions) {
            if (classroomSurveyId.equals(option.getClassroomSurveyId())) {
                options.add(option);
            }
        }
        allOptions.removeAll(options);
        options.sort(Comparator.comparing(ClassroomSurveyOptionDTO::getOptionOrder));
        return options;
    }
}
