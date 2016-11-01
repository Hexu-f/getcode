package org.hexu.getcode.view;

import org.hexu.getcode.generator.MybatisGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ConfigView extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonHelp;
    private JTextField txtDrive;
    private JTextField txtIp;
    private JTextField txtUserId;
    private JTextField txtPassword;
    private JTextField txtDaoPath;
    private JTextField txtEntityPath;
    private JTextField txtDbName;
    private JTextField txtTableName;
    private JTextField txtEntityName;
    private JTextField txtXmlPath;
    private JTextField txtDaoPackageName;
    private JTextField txtEntityPackageName;

    public ConfigView() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        buttonHelp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onHelp();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    private void onOK() throws ClassNotFoundException {
        // add your code here
        getConfig();
    }

    private void onCancel() {
        // add your code here if necessary
        this.dispose();
    }

    private void onHelp() {
        try {
            Desktop.getDesktop().browse(new URI("https://fang010603.github.io/getcode/"));
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    private void getConfig() throws ClassNotFoundException {
        String drive = txtDrive.getText();
        String ipPort = txtIp.getText();
        String userId = txtUserId.getText();
        String password = txtPassword.getText();
        String daoPath = txtDaoPath.getText();
        String entityPath = txtEntityPath.getText();
        String dbName = txtDbName.getText();
        String tableName = txtTableName.getText();
        String entityName = txtEntityName.getText();
        String xmlPath = txtXmlPath.getText();
        String daoPackageName = txtDaoPackageName.getText();
        String entityPackageName = txtEntityPackageName.getText();
        Integer re = MybatisGenerator.getInstance().getCode(drive, ipPort, dbName, userId, password, tableName, entityName, daoPath, entityPath, xmlPath, daoPackageName, entityPackageName);
        if (re == 1) {
            JOptionPane.showMessageDialog(contentPane, " 生成成功 ", " Get Code ", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(contentPane, " 生成失败 ", " Get Code ", JOptionPane.ERROR_MESSAGE);
        }

    }
}
