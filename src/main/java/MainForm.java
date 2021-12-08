import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class MainForm

{
    private JPanel mainPanel;
    private JTextField textField1;
    private JCheckBox XMLForSAMCheckBox;
    private JCheckBox DATForSASCheckBox;
    private JButton goButton;
    private JCheckBox eTokenCheckBox;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public MainForm() {
        XMLForSAMCheckBox.setSelected(true);
        DATForSASCheckBox.setSelected(true);
        eTokenCheckBox.setSelected(false);
        goButton.addActionListener(new Action() {
            @Override
            public Object getValue(String key) {
                return null;
            }

            @Override
            public void putValue(String key, Object value) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    SeedParser.name = textField1.getText();
                    if (SeedParser.name.isEmpty()){
                        throw new Exception();
                    }
                    if (DATForSASCheckBox.isSelected() && XMLForSAMCheckBox.isSelected() && !eTokenCheckBox.isSelected()) {
                        SeedParser.start("3");
                    }
                    else if (DATForSASCheckBox.isSelected() && !XMLForSAMCheckBox.isSelected() && !eTokenCheckBox.isSelected()) {
                        SeedParser.start("2");
                    }
                    else if (!DATForSASCheckBox.isSelected() && XMLForSAMCheckBox.isSelected() && !eTokenCheckBox.isSelected()) {
                        SeedParser.start("1");
                    }
                    else if (eTokenCheckBox.isSelected()) {
                        SeedParser.start("99");
                    } else {
                        throw new StringIndexOutOfBoundsException();

                    }
                    JOptionPane.showMessageDialog(mainPanel, SeedParser.message, "Успешно!", JOptionPane.PLAIN_MESSAGE);
                } catch (StringIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Одумайся!!!\nВыбери что-нибудь!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }

                catch (Exception ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Ошибка!\n Проверь:" +
                            "\n1. Название файла обязательное поле!" +
                            "\n2. Наличие в папке \\in указанного файла." +
                            "\n3. Искомые ключи должны быть из одной партии поставки. " +
                            "\nЕсли ключей в файле меньше чем нужно, возможно попытка " +
                            "\nнайти номера ключей из разных партий." +
                            "\n4. Пункт eToken 5110 используется только для упорядочивания" +
                            "\nфайла полученного путем сканирования QR кода. При любых" +
                            "\nдействиях с eToken PASS флажок должен быть снят.", "Ошибка", JOptionPane.ERROR_MESSAGE);;
                }

            }
        });
    }
}
