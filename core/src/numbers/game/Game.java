package numbers.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.List;
import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
/**
 * Created by sahinkazimov on 11/4/16.
 */
public class Game implements Screen,GestureDetector.GestureListener {

    final NumbersGame mMyGame;

    SpriteBatch batch;
    Stage stage;

    Label labelTime , labelPoint , labelNumber;
    NumbersActor numberActor;

    //shape
    Texture shape;
    float shapeHeight , shapeWidth , shapeLeft , shapeTop;

    //up_down
    Texture up_down;
    float up_downHeight , up_downWidth , up_downLeft , up_downTop;

    //timeScoreShape
    Texture timeScoreShape;
    float timeScoreShapeHeight , timeScoreShapeWidth , timeShapeLeft , timeScoreShapeTop , scoreShapeLeft;

    //settings button
    float settingsButtonHeight , settingsButtonWidth , settingsButtonLeft , settingsButtonTop;

    // randomnoe chislo tekushee , i predposlednee
    int ourNumber = -1 , lastNumber = -1;
    int countOfSuccess = 0;
    int randomParameter = 99;
    GlyphLayout title;

    //perviy uroven
    boolean firstLevel = true;
    boolean firstStep = true;
    boolean isVibration = false;
    private BitmapFont font;
    //
    boolean up = false , down = false;
    boolean success = false , fail = false;
    boolean generateNumberLet = false;

    float timeForFirstStep = 0f;
    float timeForLevel = 0f;
    float numbertHeight , numberWidth , numberLeft , numberTop;
    float gameTime = 20f;
    int gamePoints = 0;
    int successPointsBonus = 200;
    float successTimeBonus = 2;
    float failTimeBonus = 3;
    float labelTop;
    Sound successSound;

    float timeForLevelConst = 2f;


    String[] plusMinusArray = new String[]{"+" , "-"};
    String[] plusMinusMultiplyArray = new String[]{"+" , "-" , "*"};
    String[] plusMinusMultiplyArraySingle = new String[]{"+" , "-" , "*" , "!"};

    public Game(final NumbersGame numbersGame ){
        batch = new SpriteBatch();
        this.mMyGame = numbersGame;

        Gdx.input.setCatchBackKey(true);
        GestureDetector gd = new GestureDetector(this);
        Gdx.input.setInputProcessor(gd);

        stage = new Stage(new ScreenViewport());
//        Gdx.input.setInputProcessor(stage);


        font = new BitmapFont();
        successSound = Gdx.audio.newSound(Gdx.files.internal("success.mp3"));

        ourNumber = randomMethod(randomParameter);
        ourNumber = ourNumber +1;

        shape = new Texture("images/game/yellow_shape.png");
        shapeHeight = (Gdx.graphics.getHeight())*0.521f;
        shapeWidth = (shape.getWidth() * shapeHeight)/shape.getHeight();
        shapeLeft = ((Gdx.graphics.getWidth())/2) - shapeWidth/2;
        shapeTop = (Gdx.graphics.getHeight())*0.327f;

        up_down = new Texture("images/game/up_down_button.png");
        up_downHeight = (Gdx.graphics.getHeight())*0.229f;
        up_downWidth = (up_down.getWidth() * up_downHeight)/up_down.getHeight();
        up_downLeft = ((Gdx.graphics.getWidth())/2) - up_downWidth/2;
        up_downTop = (Gdx.graphics.getHeight())*0.068f;


        timeScoreShape = new Texture("images/game/time_score_shape.png");
        timeScoreShapeHeight = (Gdx.graphics.getHeight())*0.065f;
        timeScoreShapeWidth = (timeScoreShape.getWidth() * timeScoreShapeHeight)/timeScoreShape.getHeight();
        timeShapeLeft = ((Gdx.graphics.getWidth())/2) - (Gdx.graphics.getHeight())*0.263f;
        scoreShapeLeft = ((Gdx.graphics.getWidth())/2) - (Gdx.graphics.getHeight())*0.065f;
        timeScoreShapeTop = (Gdx.graphics.getHeight())*0.892f;


        Texture settingsButton = new Texture("images/startpage/settings_button.png");
        settingsButtonHeight = (Gdx.graphics.getHeight())*0.065f;
        settingsButtonWidth = (settingsButton.getWidth() * settingsButtonHeight)/settingsButton.getHeight();
        settingsButtonLeft = ((Gdx.graphics.getWidth())/2) + (Gdx.graphics.getHeight())*0.194f;
        settingsButtonTop = (Gdx.graphics.getHeight())*0.892f;



        Image settingsButtonImage = new Image(new Texture(settingsButton.getTextureData()));

        settingsButtonImage.addCaptureListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                mMyGame.setScreen(new SettingsView(mMyGame));
            }
        });

        settingsButtonImage.setPosition(settingsButtonLeft , settingsButtonTop);
        settingsButtonImage.setWidth(settingsButtonWidth);
        settingsButtonImage.setHeight(settingsButtonHeight);

        stage.addActor(settingsButtonImage);


        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = mMyGame.font;

        Label.LabelStyle labelStyleNumber = new Label.LabelStyle();
        labelStyleNumber.font = mMyGame.fontNumber;

        labelNumber = new Label(Integer.toString(ourNumber), labelStyleNumber);

//        numbertHeight = (Gdx.graphics.getHeight())*0.274f;
//        numberWidth = (labelNumber.getWidth() * numbertHeight)/labelNumber.getHeight();
        numberLeft = shapeLeft + shapeWidth/2 -  labelNumber.getWidth()/2;
        numberTop = shapeTop + shapeHeight/2 - labelNumber.getHeight()/2;

        labelNumber.setOrigin(numberLeft  , numberTop);
        labelNumber.setPosition(numberLeft  , numberTop);
//        labelNumber.setFontScale(Gdx.graphics.getHeight()*0.1374f);
//        labelNumber.setOrigin();
//        labelNumber.setPosition();


        labelTime = new Label(Integer.toString((int)gameTime), labelStyle);
        labelPoint = new Label(Integer.toString(gamePoints), labelStyle);

        //float labelTop = (Gdx.graphics.getHeight())*0.892f + timeScoreShapeHeight/3;
        labelTop = timeScoreShapeTop + timeScoreShapeHeight/2;

        labelTime.setOrigin(timeShapeLeft + timeScoreShapeWidth/2 -  labelTime.getWidth()/2 ,labelTop - labelTime.getHeight()/2);
        labelTime.setPosition(timeShapeLeft + timeScoreShapeWidth/2   - labelTime.getWidth()/2,labelTop - labelTime.getHeight()/2);

        labelPoint.setOrigin(scoreShapeLeft + timeScoreShapeWidth/2 -  labelPoint.getWidth()/2 , labelTop - labelPoint.getHeight()/2);
        labelPoint.setPosition(scoreShapeLeft + timeScoreShapeWidth/2 - labelPoint.getWidth()/2 , labelTop - labelPoint.getHeight()/2);
        stage.addActor(labelPoint);
        stage.addActor(labelTime);
        stage.addActor(labelNumber);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(213/255f, 196/255f, 137/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        mMyGame.batch.begin();
        mMyGame.batch.draw(shape , shapeLeft, shapeTop , shapeWidth , shapeHeight);
        mMyGame.batch.draw(timeScoreShape , timeShapeLeft, timeScoreShapeTop , timeScoreShapeWidth , timeScoreShapeHeight);
        mMyGame.batch.draw(timeScoreShape , scoreShapeLeft, timeScoreShapeTop , timeScoreShapeWidth , timeScoreShapeHeight);
        mMyGame.batch.draw(up_down , up_downLeft, up_downTop , up_downWidth , up_downHeight);
        mMyGame.batch.end();

        stage.act(delta);
        stage.draw();
    }

    private int randomMethod(int i){
        Random r = new Random();
        int number = r.nextInt(i);
        number = number +1;
        return number;
    }

    public String getAction(){
        Random r = new Random();
        int x = r.nextInt(2);

        return plusMinusArray[x];
    }

    public String getActionSecond(){
        Random r = new Random();
        int x = r.nextInt(3);

        return plusMinusMultiplyArray[x];
    }

    public String getActionThird(){
        Random r = new Random();
        int x = r.nextInt(4);

        return plusMinusMultiplyArraySingle[x];
    }


    //multiplyAction
    public void multiplyAction(){
        int firstNumber = randomMethod(randomParameter);
        int secondNumber = randomMethod(randomParameter);

        if (firstNumber * secondNumber == lastNumber) {
            firstNumber = firstNumber + 1;
            up = true;
            down = false;
        }else if(firstNumber * secondNumber > lastNumber){

            up = true;
            down = false;
        }else{
            down = true;
            up = false;
        }

        labelNumber.setText(firstNumber +" * " + secondNumber);
        ourNumber = firstNumber * secondNumber;
    }

    //devideAction
    public void devideAction(){

    }

    //sumAction
    public void sumAction(){
        int firstNumber = randomMethod(randomParameter);
        int secondNumber = randomMethod(randomParameter);

        if (firstNumber + secondNumber == lastNumber) {
            firstNumber = firstNumber + 1;
            up = true;
            down = false;
        }else if(firstNumber + secondNumber > lastNumber){

            up = true;
            down = false;
        }else{
            down = true;
            up = false;
        }

        labelNumber.setText(firstNumber +" + " + secondNumber);
        ourNumber = firstNumber + secondNumber;
    }

    //minusAction
    public void minusAction(){
        int firstNumber = randomMethod(randomParameter);
        int secondNumber = randomMethod(firstNumber);

        if (firstNumber - secondNumber == lastNumber) {
            firstNumber = firstNumber + 1;
            up = true;
            down = false;
        }else if(firstNumber - secondNumber > lastNumber){

            up = true;
            down = false;
        }else if(firstNumber - secondNumber < lastNumber){
            down = true;
            up = false;
        }

        labelNumber.setText(firstNumber +" - " + secondNumber);
        ourNumber = firstNumber - secondNumber;
    }

    public void withoutAction(){
        int number = randomMethod(randomParameter);
        if (number == lastNumber) {
            number = number +1;
        } else if (number > lastNumber) {
            up = true;
            down = false;

        } else if (number < lastNumber) {
            down = true;
            up = false;
        }

        ourNumber = number;
        labelNumber.setText(Integer.toString(ourNumber));
    }
    private void generateNumber(){
        lastNumber = ourNumber;
        if(countOfSuccess >  140){
            String action = getActionThird();

            if(action.equals("+")){
                sumAction();
            }else if(action.equals("-")){
                minusAction();
            }else if(action.equals("*")){
                multiplyAction();
            }else if(action.equals("!")){
                withoutAction();
            }
            numberLeft = shapeLeft + shapeWidth / 2 - labelNumber.getPrefWidth() / 2;
            labelNumber.setOrigin(numberLeft, numberTop);
            labelNumber.setPosition(numberLeft, numberTop);

        }else if(countOfSuccess >110 && countOfSuccess <=  140){
            String action = getActionSecond();

            if(action.equals("+")){
                sumAction();
            }else if(action.equals("-")){
                minusAction();
            }else if(action.equals("*")){
                multiplyAction();
            }

            numberLeft = shapeLeft + shapeWidth / 2 - labelNumber.getPrefWidth() / 2;
            labelNumber.setOrigin(numberLeft, numberTop);
            labelNumber.setPosition(numberLeft, numberTop);


        }else if(countOfSuccess > 45 && countOfSuccess <= 110){
            String action = getAction();
            if(action.equals("+")){
                sumAction();
            }else if(action.equals("-")){
                minusAction();
            }
            numberLeft = shapeLeft + shapeWidth / 2 - labelNumber.getPrefWidth() / 2;
            labelNumber.setOrigin(numberLeft, numberTop);
            labelNumber.setPosition(numberLeft, numberTop);
        }else {
            int number = randomMethod(randomParameter);
            if (number == lastNumber) {
                generateNumberLet = true;
            } else if (number > lastNumber) {
                ourNumber = number;
                up = true;
                down = false;
                labelNumber.setText(Integer.toString(ourNumber));
            } else if (number < lastNumber) {
                ourNumber = number;
                down = true;
                up = false;
                labelNumber.setText(Integer.toString(ourNumber));
            }
            numberLeft = shapeLeft + shapeWidth / 2 - labelNumber.getPrefWidth() / 2;
            labelNumber.setOrigin(numberLeft, numberTop);
            labelNumber.setPosition(numberLeft, numberTop);
        }


    }

    private void update( float delta){

        if(countOfSuccess > 15){
            timeForLevelConst = 1.5f;
            failTimeBonus = 4;
        }

        if(countOfSuccess > 25){
            timeForLevelConst = 1f;
            failTimeBonus = 5;
        }

        if(countOfSuccess > 35){
            successTimeBonus = 2;
            successPointsBonus = 300;
            randomParameter = 999;
            timeForLevelConst = 1.5f;
            failTimeBonus = 6;
        }

        if(countOfSuccess > 45){
            randomParameter = 10;
            timeForLevelConst = 3f;
            failTimeBonus = 4;
        }

        if(countOfSuccess > 47){
            timeForLevelConst = 2.5f;
        }

        if(countOfSuccess > 60){
            randomParameter = 50;
            timeForLevelConst = 3f;
        }

        if(countOfSuccess > 70){
            timeForLevelConst = 2.5f;
            failTimeBonus = 6;
        }

        if(countOfSuccess > 80){
            randomParameter = 99;
            timeForLevelConst = 3f;
            failTimeBonus = 5;
        }

        if(countOfSuccess > 90){
            timeForLevelConst = 2.5f;
            failTimeBonus = 6;
        }

        if(countOfSuccess > 110){
            randomParameter = 10;
            timeForLevelConst = 2.5f;
            failTimeBonus = 7;
        }

        if(countOfSuccess > 125){
            randomParameter = 30;
        }
        if(firstStep){
            timeForFirstStep += delta;

            if(timeForFirstStep > 2f){

                generateNumber();
                timeForFirstStep = 0;
                firstStep = false;
            }
        }else{
            timeForLevel += delta;

            if(timeForLevel > timeForLevelConst && generateNumberLet == false){
                gameTime = gameTime - failTimeBonus;
                generateNumberLet = true;
                timeForLevel = 0;
            }

            if(success){
                success = false;
                successSound.play(mMyGame.soundVolume);
                gameTime = gameTime + successTimeBonus;
                gamePoints = gamePoints + successPointsBonus;
                generateNumberLet = true;
                timeForLevel = 0;
            }

            if(fail){
                fail = false;
                gameTime = gameTime - failTimeBonus;
                timeForLevel = 0;
                generateNumberLet = true;
//                if(!isVibration) {
                    Gdx.input.vibrate(200);
    //                    isVibration = true;
//                }
            }

        }


        if(generateNumberLet){
            generateNumberLet = false;
            generateNumber();
        }

        gameTime -= delta;

        if(gameTime == 0 || gameTime < 0 ){
            mMyGame.setScreen(new ScoreScreen(mMyGame , gamePoints));
        }
        labelTime.setText(Integer.toString((int)gameTime));


        labelPoint.setText(Integer.toString(gamePoints));

        labelTime.setOrigin(timeShapeLeft + timeScoreShapeWidth/2 -  labelTime.getPrefWidth()/2 ,labelTop - labelTime.getHeight()/2);
        labelTime.setPosition(timeShapeLeft + timeScoreShapeWidth/2   - labelTime.getPrefWidth()/2,labelTop - labelTime.getHeight()/2);

        labelPoint.setOrigin(scoreShapeLeft + timeScoreShapeWidth/2 -  labelPoint.getPrefWidth()/2 , labelTop - labelPoint.getHeight()/2);
        labelPoint.setPosition(scoreShapeLeft + timeScoreShapeWidth/2 - labelPoint.getPrefWidth()/2 , labelTop - labelPoint.getHeight()/2);
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

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        if(velocityY < 0){
            if(up){
                success = true;
                countOfSuccess ++;
            }else{
                fail = true;
            }
        }else if(velocityY > 0){
            if(down){
                success = true;
                countOfSuccess ++;
            }else{
                fail = true;
            }
        }
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

}
