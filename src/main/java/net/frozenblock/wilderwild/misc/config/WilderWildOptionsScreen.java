package net.frozenblock.wilderwild.misc.config;

import com.mojang.blaze3d.vertex.PoseStack;
import com.terraformersmc.modmenu.config.ModMenuConfigManager;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class WilderWildOptionsScreen extends OptionsSubScreen {

    private final Screen previous;
    private OptionsList list;

    @SuppressWarnings("resource")
    public WilderWildOptionsScreen(Screen previous) {
        super(previous, Minecraft.getInstance().options, Component.translatable("wilderwild.options"));
        this.previous = previous;
    }

    @Override
    protected void init() {
        this.list = new OptionsList(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
        this.list.addSmall(WilderWildConfig.asOptions()); //TODO: split each config option into its own button so we can add tooltips and stuff
        this.addWidget(this.list);
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height - 27, 200, 20, CommonComponents.GUI_DONE, (button) -> {
            ModMenuConfigManager.save();
            this.minecraft.setScreen(this.previous);
        }));
    }

    @Override
    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.list.render(matrices, mouseX, mouseY, delta);
        drawCenteredString(matrices, this.font, this.title, this.width / 2, 5, 0xffffff);
        super.render(matrices, mouseX, mouseY, delta);
        List<FormattedCharSequence> list = tooltipAt(this.list, mouseX, mouseY);
        if (list != null) {
            this.renderTooltip(matrices, list, mouseX, mouseY);
        }

    }

    @Override
    public void removed() {
        WilderWildConfigManager.save();
        WilderWild.RENDER_TENDRILS = WilderWildConfig.MC_LIVE_SENSOR_TENDRILS.getValue();
    }
}
