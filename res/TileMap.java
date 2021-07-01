package res;

import javafx.scene.layout.Region;

/**
 *
 * @author Kiryl Matusevich (cyrill-m@mail.ru)
 */
public class TileMap extends Region {
    private int iStatus;                // 0..4 статус участка
    private int iCost;                  // Стоимость участка
    private int iChest;                 // Кол-во кладов
    private boolean bOwned;             // Участок в собственности
    private final int MAX_CHEST = 12;   // Max кол-во кладов
    
    // Конструктор
    /**
     *
     * @param status статус участка (уровень выработки от 0 до 4)
     * @param cost стоимость участка
     * @param size размер участка
     * @param posX координата X участка
     * @param posY координата Y участка
     */
    public TileMap(int status, int cost, int size, int posX, int posY) {
        super();
        // устанавливаем iStatus
        if (status >= 0 & status < 5) {
            iStatus = status;
            updateID();
        } else {
            iStatus = 0;
            updateID();
        }
        // устанавливаем iCost
        this.iCost = cost;
        // устанавливаем iChest
        setChest();
        // устанавливаем bOwned
        this.bOwned = false;
        // устанавливаем размер и положение участка
        this.setPrefSize(size, size);
        this.setLayoutX(posX * size);
        this.setLayoutY(posY * size);        
    }
    
    public String getInfo(){
        String info;
        info = "Cost: \n  - " + iCost + 
               "\nOwned: \n  - " + (bOwned ? "Yes" : "No") +
               "\nChest: \n  - " + iChest +
               "\nPotential: \n  - " + getPotential() +
               "\nLandscape: \n  - " + "Forest";
        return info;
    }
    
    private String getPotential() {
        switch (iStatus) {
            case 0: return "Excellent";
            case 1: return "Good";
            case 2: return "Not bad";
            case 3: return "Poorly";
            case 4: return "Almost empty";
            default: return "Undefined";
        }
    }
    
    public int getStatus() {
        return iStatus;
    }
    
    public void changeStatus() {
        if (iStatus < 4) {
            iStatus++;
            updateID();
            setChest();
        }
    }
    
    private void updateID() {
        this.setId("tile-m-" + iStatus);
    }
    
    public int getCost() {
        return iCost;
    }
    
    public void setCost(int cost) {
        iCost = cost;
    }
    
    public int getChest() {
        return iChest;
    }
    
    private void setChest() {
        iChest = MAX_CHEST - iStatus * 2;
    }
    
    public boolean getOwned() {
        return bOwned;
    }
    
    public void buyPlot() {
        bOwned = true;
    }
    
}
