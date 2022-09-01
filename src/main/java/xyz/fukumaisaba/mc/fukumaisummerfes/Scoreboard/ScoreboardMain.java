package xyz.fukumaisaba.mc.fukumaisummerfes.Scoreboard;

import jdk.internal.org.jline.utils.Display;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.Objects;

/**
 * <summary>
 *      ScoreboardMainクラス。1プレイヤー当たり1インスタンスとなります。
 */
public class ScoreboardMain {
    private Player __player;
    private ArrayList<String> __Texts;
    private String __DisplayName;

    private Objective objective;
    private ScoreboardManager scoreboardManager;
    private Scoreboard Scoreboard;
    private ArrayList<Score> __Scores;
    /**
     * <summary>
     *      ScoreboardMainクラスのインスタンスを取得します。
     */
    public ScoreboardMain(Player player,ArrayList<String> Texts,String Displayname){
        __player=player;
        __Texts=Texts;
        __DisplayName= Displayname;

        UpdateScoreboard();
    }

    /**
     * <summary>
     *      インスタンスに割り当てられているプレイヤーを取得します。
     */
    public Player getPlayer(){
        return __player;
    }

    /**
     * <summary>
     *      スコアボードに表示されているテキストを取得します。
     *      Line: 行
     */
    public String getText(int Line){
        if(Line>=__Texts.size()){
            return null;
        }else {
            return __Texts.get(Line);
        }
    }
    /**
     * <summary>
     *      スコアボードに表示されているテキストすべてを取得します。
     */

    public ArrayList<String> getAllText(){
        return __Texts;
    }
    /**
     * <summary>
     *      スコアボードのディスプレイネームを取得します。
     */
    public String getDisplayName(){
        return __DisplayName;
    }
    /**
     * <summary>
     *      スコアボードのテキストを変更します。Lineは現在のテキストの行数より少ない必要があります。適用は自動で行われます。
     */
    public void setText(int Line,String Text){
        __Texts.set(Line,Text);
        UpdateScoreboard();
    }
    /**
     * <summary>
     *      スコアボードのすべてのテキストを変更します。適用は自動で行われます。
     */
    public void setAllText(ArrayList<String> Texts){
        __Texts=Texts;
        UpdateScoreboard();
    }
    /**
     * <summary>
     *      スコアボードにテキストを追加します。適用は自動で行われます。
     */
    public void addText(String Text){
        __Texts.add(Text);
        UpdateScoreboard();
    }
    /**
     * <summary>
     *      スコアボードのテキストを削除します。Lineは行を示しており、これは現在のテキストの行数よりも少ない必要があります。適用は自動で行われます。
     */
    public void removeText(int Line){
        __Texts.remove(Line);
        UpdateScoreboard();
    }
    /**
     * <summary>
     *      スコアボードのディスプレイネームを変更します。適用は自動で行われます。
     */
    public void setDisplayname(String name){
        __DisplayName=name;
        UpdateScoreboard();
    }

    private void UpdateScoreboard() {
        __Scores.clear();
        if (objective != null) {
            objective.unregister();
        }
        scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard = scoreboardManager.getNewScoreboard();
        objective = Scoreboard.registerNewObjective(__player.getName(), "dummy", __DisplayName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(__DisplayName);
        for (int i = __Texts.size(); i > 0; i--) {
            Score score = objective.getScore(__Texts.get(__Texts.size() - i));
            score.setScore(i);
            __Scores.add(score);
        }
    }
}
