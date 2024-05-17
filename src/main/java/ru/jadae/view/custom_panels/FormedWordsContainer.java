package ru.jadae.view.custom_panels;

import lombok.Getter;
import ru.jadae.view.utils.Styles;

import javax.swing.*;
import java.awt.*;

@Getter
public class FormedWordsContainer extends JPanel {
    private final int cornerRadius;

    public FormedWordsContainer(int radius) {
        super();
        cornerRadius = radius;
        setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        graphics.setColor(Styles.BORDER_COLOR);
        graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
    }
}
