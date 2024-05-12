package ru.jadae.view.custom_panels;


import ru.jadae.view.utils.AppStyles;

import javax.swing.*;
import java.awt.*;

public class CustomActionButton extends JButton{

        private final Dimension arcs = new Dimension(15, 15);

        public CustomActionButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setOpaque(false);
            setFocusable(false);
            setForeground(Color.WHITE);
            setBackground(AppStyles.BUTTON_COLOR);
            setFont(AppStyles.LABEL_FONT);
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) { setBackground(Color.decode("#6889CA")); }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    setBackground(AppStyles.BUTTON_COLOR);
                }
            });
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
        }

}
