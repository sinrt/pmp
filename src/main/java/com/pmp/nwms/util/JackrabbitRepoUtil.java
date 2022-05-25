package com.pmp.nwms.util;

import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.model.FilePathInfoModel;
import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.rmi.repository.URLRemoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jcr.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class JackrabbitRepoUtil {
    private static final Logger log = LoggerFactory.getLogger(JackrabbitRepoUtil.class);

    @Autowired
    private NwmsConfig nwmsConfig;

    private Repository repository;

    @PostConstruct
    public void prepare() {
        try {
            repository = new URLRemoteRepository(nwmsConfig.getJackrabbitRmiRoot());
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    public void uploadFileInfo(InputStream stream, String name, String contentType, String... subPath) {
        Session session = null;
        Binary binary = null;
        try {
            session = repository.login(new SimpleCredentials(nwmsConfig.getJackrabbitUsername(), nwmsConfig.getJackrabbitPassword()));
            Node folder = session.getRootNode();
            for (int i = 0; i < subPath.length; i++) {
                String s = subPath[i];
                if (!folder.hasNode(s)) {
                    folder = folder.addNode(s);
                } else {
                    folder = folder.getNode(s);
                }
            }
            Node file = folder.addNode(name, "nt:file");
            Node content = file.addNode("jcr:content", "nt:resource");
            binary = session.getValueFactory().createBinary(stream);
            content.setProperty("jcr:data", binary);
            content.setProperty("jcr:mimeType", contentType);
            session.save();
        } catch (Exception e) {
            throw new RuntimeException(e); //todo handle exception properly
        } finally {
            if (session != null) {
                session.logout();
            }
            if (binary != null) {
                try {
                    binary.dispose();
                } catch (Exception e) {
                    log.warn(e.getMessage(), e);
                }
            }
            try {
                stream.close();
            } catch (IOException e) {
                log.warn(e.getMessage(), e);
            }
        }

    }

    public byte[] getFileInfoContent(String subPath, String name) {
        Session session = null;
        try {
            session = repository.login(new SimpleCredentials(nwmsConfig.getJackrabbitUsername(), nwmsConfig.getJackrabbitPassword()));
            Node folder = session.getRootNode().getNode(subPath);
            Node file = folder.getNode(name);
            Node content = file.getNode("jcr:content");
            String path = content.getPath();
            Binary bin = session.getNode(path).getProperty("jcr:data").getBinary();
            InputStream stream = bin.getStream();
            return IOUtils.toByteArray(stream);
        } catch (Exception e) {
            throw new RuntimeException(e); //todo handle exception properly
        } finally {
            if (session != null) {
                session.logout();
            }
        }
    }

    public void remove(String subPath, String name) {
        log.info("going to remove " + name + " from " + subPath);
        Session session = null;
        try {
            session = repository.login(new SimpleCredentials(nwmsConfig.getJackrabbitUsername(), nwmsConfig.getJackrabbitPassword()));
            Node folder = session.getRootNode().getNode(subPath);
            Node file = folder.getNode(name);
            file.remove();
            session.save();
            if (!folder.hasNodes()) {
                removeFolders(folder, session);
            }
        } catch (Exception e) {
            throw new RuntimeException(e); //todo handle exception properly
        } finally {
            if (session != null) {
                session.logout();
            }
        }
    }

    private void removeFolders(Node folder, Session session) {
        try {
            log.info("removeFolders called for " + folder.getName() + ", hasNodes : " + folder.hasNodes());
        } catch (RepositoryException e) {
            log.warn(e.getMessage(), e);
        }
        /*try {
            while (folder != null) {
                Node parent = null;
                try {
                    parent = folder.getParent();
                } catch (RepositoryException e) {
                }
                folder.remove();
                session.save();
                if (parent != null && parent.hasNodes()) {
                    folder = parent;
                } else {
                    folder = null;
                }
            }
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }*/
    }

    public void removeBatch(List<FilePathInfoModel> files) {
        //todo must be tested
        Session session = null;
        try {
            session = repository.login(new SimpleCredentials(nwmsConfig.getJackrabbitUsername(), nwmsConfig.getJackrabbitPassword()));
            List<String> subPaths = new ArrayList<>();
            for (FilePathInfoModel model : files) {
                log.info("going to removeBatch " + model.getName() + " from " + model.getSubPath());
                try {
                    Node folder = session.getRootNode().getNode(model.getSubPath());
                    Node file = folder.getNode(model.getName());
                    file.remove();
                    if (!subPaths.contains(model.getSubPath())) {
                        subPaths.add(model.getSubPath());
                    }
                } catch (Exception e) {
                    log.warn(e.getClass().getName() + " - " + e.getMessage());
                }
            }
            session.save();
            for (String subPath : subPaths) {
                Node folder = session.getRootNode().getNode(subPath);
                if (!folder.hasNodes()) {
                    try {
                        removeFolders(folder, session);
                    } catch (Exception e) {
                        log.warn(e.getClass().getName() + " - " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e); //todo handle exception properly
        } finally {
            if (session != null) {
                session.logout();
            }
        }

    }
}
