package numbers.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

/**
 * Created by sahinkazimov on 10/7/16.
 */
public class NumbersActor extends Actor {
    private Texture mTexture;
    float width;
    float height;

    public void setNewTexture(Texture texture){
        this.mTexture = texture;
    }



    public NumbersActor(Texture texture,
                        float left,
                        float top,
                        float width,
                        float height
                       ){
        this.mTexture = texture;
        this.setPosition(left,top);
        this.width = width;
        this.height = height;

        this.setBounds( this.getX() , this.getY() , this.width , this.height);
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {

        Color color = getColor();

        batch.setColor(color.r, color.g, color.b, color.a );
        batch.draw(this.mTexture , this.getX() , this.getY(), width , height);
        batch.setColor(color.r, color.g, color.b, 1f);
    }

}