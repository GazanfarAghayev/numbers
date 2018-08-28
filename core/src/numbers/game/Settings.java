package numbers.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by shahin.kazimov on 6/16/2017.
 */

public class Settings {
    public Skin generateSkin(BitmapFont font ){
        Skin skin = new Skin();

        skin.add("background" , new Texture("images/settings/scale_1.png") );
        skin.add("knob" , new Texture("images/settings/scroller.png") );
        skin.add("backgroundD" , new Texture("images/settings/scale_1.png") );
        skin.add("backgroundA" , new Texture("images/settings/scale_2.png") );

        OwnSlider.SliderStyle testStyle = new OwnSlider.SliderStyle();
        testStyle.background = skin.getDrawable("background");
        //testStyle.background.setMinHeight((float)(Gdx.graphics.getHeight()*0.1128)/3f);
//        testStyle.background.setLeftWidth(Gdx.graphics.getHeight()*0.005f);
//        testStyle.background.setRightWidth(Gdx.graphics.getHeight()*0.005f);
        testStyle.background.setTopHeight(Gdx.graphics.getHeight()*0.005f);
        testStyle.background.setBottomHeight(Gdx.graphics.getHeight()*0.005f);
        testStyle.background.setMinWidth((float)(Gdx.graphics.getWidth() * 0.34));

        testStyle.knobBefore= skin.getDrawable("backgroundA");
        //testStyle.knobBefore.setMinHeight((float)(Gdx.graphics.getHeight()*0.1128)/4.5f);
        testStyle.knobBefore.setMinWidth((float) (Gdx.graphics.getWidth() * 0.34));

        testStyle.knobAfter= skin.getDrawable("backgroundD");
        //testStyle.knobAfter.setMinHeight((float)(Gdx.graphics.getHeight()*0.1128)/4.5f);
        testStyle.knobAfter.setMinWidth((float) (Gdx.graphics.getWidth() * 0.34));

        testStyle.knob = skin.getDrawable("knob");
        testStyle.knob.setMinHeight((float)(Gdx.graphics.getHeight()*0.0644));
        testStyle.knob.setMinWidth((float) (Gdx.graphics.getHeight() * 0.0644));

        skin.add("defStyle" , testStyle );

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        skin.add("labelStyle" , labelStyle);

        CheckBox.CheckBoxStyle checkBoxStyle = new CheckBox.CheckBoxStyle();
        skin.add("checkBoxOff" , new Texture("images/settings/switcher_off.png"));
        skin.add("checkBoxOn" , new Texture("images/settings/switcher_on.png"));

        Texture checkBox = new Texture("images/settings/switcher_on.png");
        float checkBoxHeight = Gdx.graphics.getHeight() * 0.06f;
        float checkBoxWidth = (checkBox.getWidth() * checkBoxHeight)/checkBox.getHeight();

        checkBoxStyle.checkboxOff = skin.getDrawable("checkBoxOff");
        checkBoxStyle.checkboxOff.setMinHeight(checkBoxHeight);
        checkBoxStyle.checkboxOff.setMinWidth(checkBoxWidth);
        checkBoxStyle.checkboxOn = skin.getDrawable("checkBoxOn");
        checkBoxStyle.checkboxOn.setMinWidth(checkBoxWidth);
        checkBoxStyle.checkboxOn.setMinHeight(checkBoxHeight);
        checkBoxStyle.font = font;

        skin.add("checkboxStyle" , checkBoxStyle);
        return skin;
    }
}

