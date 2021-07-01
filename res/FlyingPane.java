package res;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 * @author Kiryl Matusevich (cyrill-m@mail.ru)
 */



public class FlyingPane extends Pane {
        
    private TranslateTransition tt;
    private boolean isAnim;
    private final int DIST;
    
    public FlyingPane(Node node, int dist){
        DIST = dist;
        tt = new TranslateTransition(Duration.seconds(1), node);
        isAnim = false;        
        tt.setOnFinished(e -> {isAnim = false;            
            if (!Game.GAME_START && (this.getAccessibleText() == "PaneGame")) {
                //MainFXMLController.paneGame.getChildren().clear();
            }            
        });
        tt.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double t) {
                //return (t == 0.0) ? 0.0 : Math.pow(2.0, 10 * (t -1));
                return (t == 1.0) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
            }                
        });
    }

    public FlyingPane(int w, int h){
        this.setPrefSize(w, h);
        DIST = 432;
        tt = new TranslateTransition(Duration.seconds(1), this);
        isAnim = false;        
        tt.setOnFinished(e -> isAnim = false);
        tt.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double t) {
                //return (t == 0.0) ? 0.0 : Math.pow(2.0, 10 * (t -1));
                return (t == 1.0) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
            }                
        });
    }
    
    public void animate(){
        if (isAnim) return;
        isAnim = true;
        tt.setFromX(getTranslateX());        
        if (getTranslateX() == 0){
            tt.setToX(getTranslateX() + DIST);
        } else {tt.setToX(getTranslateX() - DIST);}
        tt.play();
    }
    
    public void animateX(int fromX){
        if (isAnim) return;
        isAnim = true;
        tt.setFromX(fromX);        
        if (fromX == 0){
            tt.setToX(fromX + DIST);
            slowStart();
        } else {tt.setToX(fromX - DIST);
                slowFinish();}
        tt.play();
    }
    
    public void animateY(int fromY){
        if (isAnim) return;
        isAnim = true;
        tt.setFromY(fromY);        
        if (fromY == 0){
            tt.setToY(fromY + DIST);
            slowStart();
        } else {tt.setToY(fromY - DIST);
                slowFinish();}
        tt.play();
    }
    
    public void slowStart(){
        tt.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double t) {
                return (t == 0.0) ? 0.0 : Math.pow(2.0, 10 * (t -1));
                //return (t == 1.0) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
            }                
        });
    }
    
    public void slowFinish(){
        tt.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double t) {
                //return (t == 0.0) ? 0.0 : Math.pow(2.0, 10 * (t -1));
                return (t == 1.0) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
            }                
        });
    }
}
