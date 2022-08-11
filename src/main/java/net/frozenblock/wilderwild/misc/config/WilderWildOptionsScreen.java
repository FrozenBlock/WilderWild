package net.frozenblock.wilderwild.misc.config;

import com.terraformersmc.modmenu.config.ModMenuConfigManager;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

import java.util.List;

public class WilderWildOptionsScreen extends GameOptionsScreen {

    private final Screen previous;
    private ButtonListWidget list;

    @SuppressWarnings("resource")
    public WilderWildOptionsScreen(Screen previous) {
        super(previous, MinecraftClient.getInstance().options, Text.translatable("wilderwild.options"));
        this.previous = previous;
    }


    protected void init() {
        this.list = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
        this.list.addAll(WilderWildConfig.asOptions()); //TODO: split each config option into its own button so we can add tooltips and stuff
        this.addSelectableChild(this.list);
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.DONE, (button) -> {
            ModMenuConfigManager.save();
            this.client.setScreen(this.previous);
        }));
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.list.render(matrices, mouseX, mouseY, delta);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 5, 0xffffff);
        super.render(matrices, mouseX, mouseY, delta);
        List<OrderedText> list = getHoveredButtonTooltip(this.list, mouseX, mouseY);
        if (list != null) {
            this.renderOrderedTooltip(matrices, list, mouseX, mouseY);
        }

    }

    public void removed() {
        WilderWildConfigManager.save();
        WilderWild.RENDER_TENDRILS = WilderWildConfig.MC_LIVE_SENSOR_TENDRILS.getValue();
    }
}
