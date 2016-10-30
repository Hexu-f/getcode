package org.hexu.getcode.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.hexu.getcode.view.ConfigView;

import javax.swing.*;

/**
 * Created by hexu on 16/10/29.
 */
public class MybatisGeneratorAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        JFrame frame = new JFrame("配置信息");
        frame.setContentPane(new ConfigView().getContentPane());
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 500);
        frame.setVisible(true);
    }
}
