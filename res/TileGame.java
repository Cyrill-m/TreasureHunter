package res;

import java.util.Random;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 *
 * @author Kiryl Matusevich (cyrill-m@mail.ru)
 */
public class TileGame extends Pane {
    public final Region rgFront;        // 
    private final TileGameLand tglLand; // ��� ���������
    private final int iLevel;           // ������� �������
    private final int iOpenCost;        // ���� �������� �������
    private int iState;                 // ��������� (���� 9, ����� 0, ����� ������ ������ 1..8)
    public boolean bOpen;               // ������� ������
    public int x, y;
    
    /**
     *
     * @param land ��� ���������
     * @param level ������� �������
     * @param state ��������� (���� 9, ����� 0, ����� ������ ������ 1..8)
     * @param size ������ �������
     * @param posX ���������� X �������
     * @param posY ���������� Y �������
     */
    public TileGame(TileGameLand land, int level, int state, int size, int posX, int posY) {
        super();
        tglLand = land;
        iLevel = level;
        iState = state;
        x = posX;
        y = posY;
        bOpen = false;
        
        // ������������� ������ � ��������� �������
        this.setPrefSize(size, size);
        this.setLayoutX(posX * size);
        this.setLayoutY(posY * size);
        
        // ������������� ��� ���������
        Random rr = new Random();
        switch (tglLand) {
            case GRASS:
                iOpenCost = 2;
                this.setId("tile-g-0" + rr.nextInt(3));
                break;
            case TREE:
                iOpenCost = 4;
                this.setId("tile-g-1" + rr.nextInt(3));
                break;
            case SWAMP:
                iOpenCost = 6;
                this.setId("tile-g-2" + rr.nextInt(3));
                break;
            case MOUNT:
                iOpenCost = 10;
                this.setId("tile-g-3" + rr.nextInt(3));
                break;
            default:
                iOpenCost = 2;
                this.setId("tile-g-0" + rr.nextInt(3));
                break;
        }
        rgFront = new Region();
        rgFront.setPrefSize(size, size);
        //rgFront.setId("tile-g-d" + state);
        rgFront.getStyleClass().add("tile-g-d");
        rgFront.setOnMousePressed(e -> {
            //������� ��� ������?
            if (bOpen) {return;} else {bOpen = true;}
            //���������� ���-�� ������ ������ ��� ������
            rgFront.setId("tile-g-d" + iState);
            //���� ���� ������
            if (iState == 9) {
                Game.ChangeChest();
                Game.ChangeMoney(25);//��������� �����
            }
            //��������� ������ �� �������� �������
            Game.ChangeMoney(iOpenCost * -1);
        });
        this.getChildren().add(rgFront);
    }
    
    public void setState(int state) {
        iState = state;
        //rgFront.setId("tile-g-d" + state);
    }
    
    public int getState() {
        return iState;
    }
    
    public String getInfo(){
        String info;
        info = "Cost: \n  - " + iOpenCost + 
               "\nOpen: \n  - " + (bOpen ? "Yes" : "No") +               
               "\nLandscape: \n  - " + this.tglLand;
        return info;
    }
}
