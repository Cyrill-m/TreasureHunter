/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treasurehunter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import res.FlyingPane;
import res.Game;
import res.TileGame;
import res.TileGameLand;
import res.TileMap;
/**
 *
 * @author user
 */
public class MainFXMLController implements Initializable {
    
    private TranslateTransition transTransition;
    private FlyingPane flyPaneMap, flyPaneGame, flyLeftMenu, flyRightMenu;
    private TileMap tempTileMap; //временное хранение ссылки на выбранный TileMap
    private List<TileMap> listTileMap;
    private List<TileGame> listTileGame;
    //private final int ROW_COUNT = 9, COL_COUNT = 9; //размеры карты участков
    //private final int TILE_MAP_SIZE = 48;
    //private int iMoney;
    //private boolean GAME_START;
    private Random rnd;
    
    @FXML
    public Pane paneGame;    
    @FXML
    private Pane paneRight;
    @FXML
    private StackPane paneStack;
    @FXML
    private Pane paneStart;
    @FXML
    private Button btnGame;
    @FXML
    private Button btnExit;
    @FXML
    private Pane paneLeftMenu;
    @FXML
    private Pane paneRightMenu;
    @FXML
    private Line lineMenu;
    @FXML
    private TextArea tarInfo;
    @FXML
    private Button btnBack;
    @FXML
    private Label lbMoney;
    @FXML
    private Pane paneChest;
    @FXML
    private Label lbChest;
    @FXML
    private Pane paneButtons;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;
    @FXML
    private Label lbMessage;
    @FXML
    private Pane paneGameEx;
    @FXML
    private Pane paneMap;
    @FXML
    private Label lbInfo;
    @FXML
    private Pane paneMsg;
    @FXML
    private Label lbMsg;
    @FXML
    private Button btnMsgOk;
    @FXML
    private Button btnMsgCancel;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        Game.MONEY = 200;
        lbMoney.setText(Game.GetMoney());
        //final InvalidationListener invalidationListener = observable ->
        //    lbMoney.setText("" + observable);
        //Game.Money.addListener(invalidationListener);
        
        Game.GAME_START = false;
        rnd = new Random();
        flyPaneMap = new FlyingPane(paneMap, -432);
        flyPaneGame = new FlyingPane(paneGame, 432);
        flyPaneGame.setAccessibleText("PaneGame");
        flyLeftMenu = new FlyingPane(paneLeftMenu, -180);
        flyRightMenu = new FlyingPane(paneRightMenu, 180);
        paneStack.setClip(new Rectangle(432, 432));
        
        lineMenu.getStrokeDashArray().addAll(10.0, 5.0);        
        createListTileMap();
    }
    
    
    private void createListTileMap(){
        listTileMap = new ArrayList<>(Game.ROW_COUNT * Game.COL_COUNT);
        for (int y=0; y<Game.ROW_COUNT; y++){
            for (int x=0; x<Game.COL_COUNT; x++){
                //Создание Tile-ов участков
                //0 - начальный статус
                //(ROW_COUNT*100 - у*100) - расчет стоимости участка 
                TileMap tm = new TileMap(0, (Game.ROW_COUNT*100 - y*100), Game.TILE_MAP_SIZE, x, y);
                //Обработчик наведения мыши на TileMap
                tm.setOnMouseEntered(e -> {
                    tarInfo.setText(((TileMap)e.getSource()).getInfo());
                });
                //Обработчик выхода мыши из TileMap
                tm.setOnMouseExited(e -> {
                    if (!paneGameEx.isVisible()) tarInfo.clear();
                        });
                //Обработчик щелчка мыши на TileMap
                tm.setOnMouseClicked(e -> {
                    //показываем прозрачную панель блокирующую все участки
                    //hover участков не работает
                    paneGameEx.setVisible(true);
                    //показываем панель кнопок
                    paneButtons.setVisible(true);
                    //формируем текст для tarInfo
                    tarInfo.setText(((TileMap)e.getSource()).getInfo());
                    //формируем текст метки над кнопками
                    if (((TileMap)e.getSource()).getOwned()){
                        lbMessage.setText("Reseach plot");
                    } else {lbMessage.setText("Buy and reseach");}
                    //рисуем рамку выделения участка
                    ((TileMap)e.getSource()).setStyle("-fx-border-color: #9b0000;"
                            + "-fx-border-radius: 6px; -fx-border-width: 2px;");
                    //сохраняем ссылку на выбранный участок
                    tempTileMap = (TileMap)e.getSource();
                    //((TileMap)e.getSource()).changeStatus();
                });
                listTileMap.add(tm);
                paneMap.getChildren().add(tm);
            }
        }
    }
    
    private void createListTileGame(int chests){
        paneGame.getChildren().clear();
        listTileGame = new ArrayList<>(Game.ROW_COUNT * Game.COL_COUNT);
        //Random r = new Random();
        for (int y=0; y<Game.ROW_COUNT; y++){
            for (int x=0; x<Game.COL_COUNT; x++){
                //Создание Tile-ов игрового поля
                //TileGame(int land, int level, int state, int size, int posX, int posY)
                TileGame tg = new TileGame(randomLand(40, 30, 10, 5), 
                        0, 0, Game.TILE_MAP_SIZE, x, y);
                //Обработчик наведения мыши на TileGame
                tg.setOnMouseEntered(e -> {
                    tarInfo.setText(((TileGame)e.getSource()).getInfo());
                });
                tg.setOnMouseReleased(e -> {
                    lbMoney.setText(Game.GetMoney());
                    lbChest.setText(Game.GetChest());
                    if (Game.EndGame()) msgShowEndGame();
                });
                listTileGame.add(tg);
                paneGame.getChildren().add(tg);
            }
        }
        
        //расставляем сундуки
        int c = 0;        
        //Random rand = new Random();
        while (c < chests) {
            int i = rnd.nextInt(listTileGame.size());
            if (listTileGame.get(i).getState() != 9) {
                listTileGame.get(i).setState(9);
                c++;
            }
        }
        //расставляем state участков
        for (TileGame m : listTileGame) {
            final int x = m.x;
            final int y = m.y;
            if (m.getState() == 9) {
                //System.out.println("(" + x + "," + y + ") - chest");
                continue;
            }
            long chest = listTileGame.stream()
                    .filter(ti -> ((ti.x == x) || (ti.x == x - 1) || (ti.x == x + 1))
                    && ((ti.y == y) || (ti.y == y - 1) || (ti.y == y + 1))
                    && !((ti.x == x) && (ti.y == y)))
                    .filter(ti -> ti.getState() == 9)
                    .count();
            m.setState((int) chest);            
        }
    }
    
    private TileGameLand randomLand(int rndGrass, int rndTree, int rndSwamp, int rndMount){
        TileGameLand tgl;
        int r = rnd.nextInt(rndGrass + rndTree + rndSwamp + rndMount);
        if (r < rndGrass) {
            tgl = TileGameLand.GRASS;
        } else if (r < (rndGrass + rndTree)) {
            tgl = TileGameLand.TREE;
        } else if (r < (rndGrass + rndTree + rndSwamp)) {
            tgl = TileGameLand.SWAMP;
        } else tgl = TileGameLand.MOUNT;
        return tgl;
    }

    @FXML
    private void btnGameAction(ActionEvent event) {        
        //flyPaneGame.animateX((int)paneGame.getTranslateX());
        lbChest.setText("0/0");
        flyPaneMap.animateY((int)paneMap.getTranslateY());
        flyLeftMenu.animateX((int)paneLeftMenu.getTranslateX());
        flyRightMenu.animateX((int)paneRightMenu.getTranslateX());
        Game.MAP_MODE = true;
    }

    @FXML
    private void btnBackAction(ActionEvent event) {
        //flyPaneGame.animateX((int)paneGame.getTranslateX());
        if (Game.GAME_MODE){
            msgShowBreakGame();
            //return;
        }
        
        if (Game.MAP_MODE){
            flyPaneMap.animateY((int)paneMap.getTranslateY());
            flyLeftMenu.animateX((int)paneLeftMenu.getTranslateX());
            flyRightMenu.animateX((int)paneRightMenu.getTranslateX());
            if (paneGameEx.isVisible()){
                tarInfo.clear();
                paneGameEx.setVisible(false);
                paneButtons.setVisible(false);
                tempTileMap.setStyle("-fx-border-color: transparent;");
            }
        }        
    }
    
    @FXML
    private void btnExitAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void paneGameExClicked(MouseEvent event) {
        if (paneMsg.isVisible()) return;
        tarInfo.clear();
        resetTileGameState();
    }

    @FXML
    private void btnOkAction(ActionEvent event) {
        //Проверяем что участок не куплен и НЕ достаточно денег на его покупку
        if (!Game.EnoughMoney(tempTileMap.getCost()) & !tempTileMap.getOwned()) {
            //Отключаем боковые панели
            paneLeftMenu.setDisable(true);
            paneButtons.setDisable(true);
            //Выводим панель сообщения
            msgShowNotMoney();
            return;
        }
        //Если участок не куплен - покупаем
        if (!tempTileMap.getOwned()){
            Game.ChangeMoney(-1 * tempTileMap.getCost());
            lbMoney.setText(Game.GetMoney());
            tempTileMap.buyPlot();
        }
        //Создаем карту участка
        createListTileGame(tempTileMap.getChest());
        Game.StartNewGame(tempTileMap.getChest());
        lbChest.setText(Game.GetChest());
        tempTileMap.changeStatus();
        
        flyPaneGame.animateX((int)paneGame.getTranslateX());
        Game.GAME_MODE = true;
        Game.MAP_MODE = false;
        //Game.GAME_START = true;
        resetTileGameState();
    }

    @FXML
    private void btnCancelAction(ActionEvent event) {
        tarInfo.clear();
        resetTileGameState();
    }

    @FXML
    private void btnMsgOkAction(ActionEvent event) {
        if (Game.MAP_MODE){
            paneMsg.setVisible(false);
            paneLeftMenu.setDisable(false);
            paneButtons.setDisable(false);
            resetTileGameState();      
        }
        
        if (Game.GAME_MODE){
            paneGameEx.setVisible(false);
            paneMsg.setVisible(false);
            btnMsgCancel.setVisible(false);
            paneLeftMenu.setDisable(false);
            Game.GAME_MODE = false;
            Game.MAP_MODE = true;
            lbChest.setText("0/0");
            flyPaneGame.animateX((int)paneGame.getTranslateX());
        }
    }
    
    private void resetTileGameState(){
        paneGameEx.setVisible(false);
        paneButtons.setVisible(false);
        tempTileMap.setStyle("-fx-border-color: transparent;");
    }

    private void msgShowEndGame() {
        paneGameEx.setVisible(true);
        paneMsg.setVisible(true);
        lbMsg.setText(Game.MSG_END_GAME);
        btnMsgCancel.setVisible(false);
        paneLeftMenu.setDisable(true);
    }
    
    private void msgShowNotMoney() {
        paneMsg.setVisible(true);
        lbMsg.setText(Game.MSG_NOT_MONEY);
        btnMsgCancel.setVisible(false);
    }
    
    private void msgShowBreakGame() {
        paneGameEx.setVisible(true);
        paneMsg.setVisible(true);
        lbMsg.setText(Game.MSG_BREAK_GAME);
        btnMsgCancel.setVisible(true);
        paneLeftMenu.setDisable(true);
    }

    @FXML
    private void btnMsgCancelAction(ActionEvent event) {
        paneGameEx.setVisible(false);
        paneMsg.setVisible(false);        
        paneLeftMenu.setDisable(false);
    }

}
