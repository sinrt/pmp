package com.octo.captcha.component.image.utils;

import java.awt.Toolkit;

public class ToolkitFactory {
    public static String TOOLKIT_IMPL = "toolkit.implementation";

    public static Toolkit getToolkit() {
        Toolkit defaultToolkit = null;
        try {
            String tempToolkitClass = System.getProperty(TOOLKIT_IMPL);
            if (tempToolkitClass != null) {
                defaultToolkit = (Toolkit)Class.forName(tempToolkitClass).newInstance();
            } else {
                defaultToolkit = getDefaultToolkit();
            }
        } catch (Throwable e) {
            throw new RuntimeException("toolkit has not been abble to be initialized", e);
        }
        return defaultToolkit;
    }

    private static Toolkit getDefaultToolkit() {
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        return defaultToolkit;
    }
}
