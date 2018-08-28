package numbers.game;

		import com.badlogic.gdx.Gdx;
		import com.badlogic.gdx.Preferences;
		import com.badlogic.gdx.audio.Music;
		import com.badlogic.gdx.graphics.Color;
		import com.badlogic.gdx.graphics.g2d.BitmapFont;
		import com.badlogic.gdx.graphics.g2d.SpriteBatch;
		import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class NumbersGame extends com.badlogic.gdx.Game {
	public SpriteBatch batch;
	Music backgroundMusic;
	BitmapFont font ,fontWhite , fontNumber;
	public static float soundVolume = 1;
	boolean isVibrate = true;

	@Override
	public void create() {
		Preferences prefs = Gdx.app.getPreferences("numbers");

		boolean firstRuned = false;
		float volume;


		if(prefs.getBoolean("firstRuned" , true)){
			prefs.putBoolean("firstRuned" , false);
			firstRuned = true;
		}

		if(firstRuned){
			volume = 0.1f;
			soundVolume = 0.3f;
			isVibrate = true;
		}else{
			volume = prefs.getFloat("volume");
			soundVolume = prefs.getFloat("soundVolume");
			isVibrate = prefs.getBoolean("isVibrateOn");
//			backgroundMusic.setVolume(volume);
		}

		prefs.putFloat("volume" , volume);
		prefs.putFloat("soundVolume" , soundVolume);
		prefs.putBoolean("isVibrateOn" , isVibrate);

		prefs.flush();

//		backgroundMusic.play();

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/AvenirNextRoundedPro-Boldn.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = (int)(Gdx.graphics.getHeight()*0.0374);
		parameter.color =  new Color(149/255f,87/255f,39/255f,1f);
		parameter.characters = "1234567890AaBbCcÇçDdEeƏəFfGgĞğHhXxIıİiJjKkQqLlMmNnOoÖöPpRrSsŞşTtUuÜüVvYyZz?!:";
		font = generator.generateFont(parameter);

		parameter.color = new Color(255/255f,255/255f,255/255f,1f);
		fontWhite = generator.generateFont(parameter);

		FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter2.size = (int)(Gdx.graphics.getHeight()*0.1374);
		parameter2.color = new Color(149/255f,87/255f,39/255f,1f);
		parameter2.characters = "+-*1234567890";
		fontNumber = generator.generateFont(parameter2);


		generator.dispose();

		batch = new SpriteBatch();
		this.setScreen(new FirstScreen(this));

	}

	@Override
	public void render() {
		super.render();
	}
}

