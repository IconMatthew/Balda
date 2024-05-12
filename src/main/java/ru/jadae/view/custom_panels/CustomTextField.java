package ru.jadae.view.custom_panels;

import ru.jadae.view.utils.AppStyles;

import javax.swing.*;
import java.awt.*;

public class CustomTextField extends JTextField {
    private final Dimension arcs = new Dimension(5, 5);

    public CustomTextField() {
        setFont(AppStyles.LABEL_FONT);
        setBackground(AppStyles.PRIMARY_COLOR);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { setBackground(AppStyles.SECONDARY_COLOR); }
            public void mouseExited(java.awt.event.MouseEvent evt) { setBackground(AppStyles.PRIMARY_COLOR); }
        });
    }

    public CustomTextField(String initValue) {
        this();
        setText(initValue);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
    }

    @Override
    protected void paintBorder(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.decode("#acadae"));
        graphics.drawRoundRect(1, 1, width-2, height-2, arcs.width, arcs.height);
    }
}
