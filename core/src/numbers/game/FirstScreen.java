package numbers.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by shahin.kazimov on 6/8/2017.
 */

public class FirstScreen  implements Screen {

    final NumbersGame mMyGame;

    SpriteBatch batch;
    Stage stage;


    float startButtonHeight , startButtonWidth , startButtonLeft , startButtonTop;
    float settingsButtonHeight , settingsButtonWidth , settingsButtonLeft , settingsButtonTop;

    public FirstScreen(final NumbersGame numbersGame ) {
        batch = new SpriteBatch();
        this.mMyGame = numbersGame;

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
                mMyGame.setScreen(new Game(mMyGame));
            }
        });

        startImage.setPosition(startButtonLeft , startButtonTop);
        startImage.setWidth(startButtonWidth);
        startImage.setHeight(startButtonHeight);

        Texture settingsButton = new Texture("images/startpage/settings_button.png");
        settingsButtonHeight = (Gdx.graphics.getHeight())*0.065f;
        settingsButtonWidth = (settingsButton.getWidth() * settingsButtonHeight)/settingsButton.getHeight();
        settingsButtonLeft = ((Gdx.graphics.getWidth())/2) + (Gdx.graphics.getHeight())*0.194f;
        settingsButtonTop = (Gdx.graphics.getHeight())*0.892f;



        Image settingsButtonImage = new Image(new Texture(settingsButton.getTextureData()));

        settingsButtonImage.addCaptureListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("settings click");
                mMyGame.setScreen(new SettingsView(mMyGame));
            }
        });

        settingsButtonImage.setPosition(settingsButtonLeft , settingsButtonTop);
        settingsButtonImage.setWidth(settingsButtonWidth);
        settingsButtonImage.setHeight(settingsButtonHeight);


        stage.addActor(startImage);
        stage.addActor(settingsButtonImage);
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(213/255f, 196/255f, 137/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        update(delta);
//
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
        stage.clear();
        stage.dispose();
    }


}
