package numbers.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by shahin.kazimov on 6/16/2017.
 */
public class SettingsView implements Screen{

    Stage stage;

    SpriteBatch batch;
    final NumbersGame mMyGame;

    Table settingsTable;
    Skin settingsSkin , settingsSkinVibration;

    OwnSlider s;
    OwnSlider soundVolumeSlider;

    //shape
    Texture shape;
    float shapeHeight , shapeWidth , shapeLeft , shapeTop;

    Texture settingsButton;

    float startButtonHeight , startButtonWidth , startButtonLeft , startButtonTop;
    float settingsHeight , settingsWidth , settingsLeft , settingsTop;

    GlyphLayout title;
    float titleX , titleY;
    Image soundOffImage , soundOnImage , vibrationImage;

    String text= "Settings";

    public SettingsView(final NumbersGame numbersGame){
        batch = new SpriteBatch();
        this.mMyGame = numbersGame;
        Gdx.input.setCatchBackKey(true);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);





        Texture startButton = new Texture("images/startpage/play_button.png");
        startButtonHeight = (Gdx.graphics.getHeight())*0.099f;
        startButtonWidth = (startButton.getWidth() * startButtonHeight)/startButton.getHeight();
        startButtonLeft = ((Gdx.graphics.getWidth())/2) - startButtonWidth/2;
        startButtonTop = (Gdx.graphics.getHeight())*0.101f;



        Image startImage = new Image(new Texture(startButton.getTextureData()));

        startImage.addCaptureListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

                Preferences prefs = Gdx.app.getPreferences("numbers");
                prefs.putFloat("soundVolume", mMyGame.soundVolume);
                prefs.putBoolean("isVibrateOn" , mMyGame.isVibrate);
                prefs.flush();


                mMyGame.setScreen(new Game(mMyGame));
            }
        });

        startImage.setPosition(startButtonLeft , startButtonTop);
        startImage.setWidth(startButtonWidth);
        startImage.setHeight(startButtonHeight);


        settingsButton = new Texture("images/settings/settings.png");
        settingsHeight = (Gdx.graphics.getHeight())*0.065f;
        settingsWidth = (settingsButton.getWidth() * settingsHeight)/settingsButton.getHeight();
        settingsLeft = ((Gdx.graphics.getWidth())/2) - (Gdx.graphics.getHeight())*0.239f;
        settingsTop = (Gdx.graphics.getHeight())*0.892f;


        title = new GlyphLayout(mMyGame.font, text);
        titleX = settingsLeft + settingsWidth;
        titleY = settingsTop + settingsHeight/2 + title.height/2;


        shape = new Texture("images/game/yellow_shape.png");
        shapeHeight = (Gdx.graphics.getHeight())*0.521f;
        shapeWidth = (shape.getWidth() * shapeHeight)/shape.getHeight();
        shapeLeft = ((Gdx.graphics.getWidth())/2) - shapeWidth/2;
        shapeTop = (Gdx.graphics.getHeight())*0.327f;



        Texture soundOff = new Texture("images/settings/sound_off_icon.png");
        Texture soundOn = new Texture("images/settings/sound_on_icon.png");

        float soundHeight = (Gdx.graphics.getHeight())*0.065f;
        float soundWidth = (soundOff.getWidth() * soundHeight)/soundOff.getHeight();

        soundOffImage = new Image(new Texture(soundOff.getTextureData()));
        soundOnImage = new Image(new Texture(soundOn.getTextureData()));


        soundOffImage.setWidth(soundWidth);
        soundOffImage.setHeight(soundHeight);

        soundOnImage.setWidth(soundWidth);
        soundOnImage.setHeight(soundHeight);

        Texture vibration = new Texture("images/settings/vibration_icon.png");
        vibrationImage = new Image(new Texture(vibration.getTextureData()));

        vibrationImage.setWidth(soundWidth);
        vibrationImage.setHeight(soundHeight);

        stage.addActor(startImage);
        settingsShow();

    }

    public void settingsShow(){
        Preferences prefs = Gdx.app.getPreferences("numbers");

        if(settingsTable == null) {
            Settings settings = new Settings();
            settingsSkin = settings.generateSkin(mMyGame.font);
            settingsSkinVibration = settings.generateSkin(mMyGame.font);
            settingsTable = new Table();

            float sliderWidth = (float) (Gdx.graphics.getWidth() * 0.34);
            float sliderHeight = (float) (Gdx.graphics.getHeight() * 0.0644);


            soundVolumeSlider = new OwnSlider(0, 1, 0.01f, false, settingsSkin, "defStyle");
            soundVolumeSlider.setValue(prefs.getFloat("soundVolume"));

            soundVolumeSlider.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println("soundVolume");
                    mMyGame.soundVolume = soundVolumeSlider.getValue();
                }
            });

            final CheckBox checkBox = new CheckBox("" , settingsSkin , "checkboxStyle");
            checkBox.setChecked(mMyGame.isVibrate);
            checkBox.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    mMyGame.isVibrate = checkBox.isChecked();
                }
            });

            float checkBoxHeight = Gdx.graphics.getHeight() * 0.06f;
            float checkBoxWidth = (checkBox.getWidth() * checkBoxHeight)/checkBox.getHeight();

            settingsTable.setDebug(false);


            Label.LabelStyle labelStyleNumber = new Label.LabelStyle();
            labelStyleNumber.font = mMyGame.font;

            Label nameLabel = new Label("Vibration", labelStyleNumber);

//            float numbertHeight = (Gdx.graphics.getHeight())*0.274f;
//            float numberWidth = (nameLabel.getWidth() * numbertHeight)/nameLabel.getHeight();
//            float numberLeft = (Gdx.graphics.getWidth()/2) - numberWidth/2;
//            float numberTop = (Gdx.graphics.getHeight()/2) -  numbertHeight/2;
//
////        labelNumber.setBounds(numberLeft , numberTop , numberWidth , numbertHeight);
//            nameLabel.setOrigin(numberLeft  , numberTop);
//            nameLabel.setPosition(numberLeft  , numberTop);

            stage.addActor(nameLabel);
            settingsTable.add(vibrationImage).padBottom((Gdx.graphics.getHeight()* 0.105f));
            settingsTable.add(nameLabel).padBottom((Gdx.graphics.getHeight() * 0.105f)).left();
            settingsTable.add(checkBox).padBottom((Gdx.graphics.getHeight() * 0.105f)).height(checkBoxHeight).width(checkBoxWidth);
            settingsTable.row();

            settingsTable.add(soundOffImage).padBottom((float)(Gdx.graphics.getHeight()* 0.464));
            settingsTable.add(soundVolumeSlider).height(sliderHeight).width(sliderWidth).padBottom((float)(Gdx.graphics.getHeight()* 0.464));
            settingsTable.add(soundOnImage).padBottom((float)(Gdx.graphics.getHeight()* 0.464));
            settingsTable.row();

            settingsTable.pack();

            settingsTable.setPosition(Gdx.graphics.getWidth() / 2 - settingsTable.getWidth() / 2, 0);

            stage.addActor(settingsTable);

//            settingsTable.setDebug(true);
        }
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            mMyGame.setScreen(new FirstScreen(mMyGame));
        }

        Gdx.gl.glClearColor(213/255f, 196/255f, 137/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mMyGame.batch.begin();
        mMyGame.font.draw(mMyGame.batch, title, titleX, titleY);
        mMyGame.batch.draw(settingsButton, settingsLeft , settingsTop ,settingsWidth, settingsHeight);
        mMyGame.batch.draw(shape , shapeLeft, shapeTop , shapeWidth , shapeHeight);
        mMyGame.batch.end();

        stage.act(delta);
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}