package assignment1.register;

import java.io.Serializable;
import java.util.Date;

public class UserActivity implements Serializable {
    private Date createdProfileDate;
    private int timePlayedTotal;
    private int scoreboard;
    private int timePlayedInOneLogin;

    public UserActivity(Date createdProfileDate, int timePlayedTotal, int scoreboard, int timePlayedInOneLogin) {
        this.createdProfileDate = createdProfileDate;
        this.timePlayedTotal = timePlayedTotal;
        this.scoreboard = scoreboard;
        this.timePlayedInOneLogin = timePlayedInOneLogin;
    }

    public UserActivity() {
    }

    public Date getCreatedProfileDate() {
        return createdProfileDate;
    }

    public void setCreatedProfileDate(Date createdProfileDate) {
        this.createdProfileDate = createdProfileDate;
    }

    public int getTimePlayedTotal() {
        return timePlayedTotal;
    }

    public void setTimePlayedTotal(int timePlayedTotal) {
        this.timePlayedTotal = timePlayedTotal;
    }

    public int getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(int scoreboard) {
        this.scoreboard = scoreboard;
    }

    public int getTimePlayedInOneLogin() {
        return timePlayedInOneLogin;
    }

    public void setTimePlayedInOneLogin(int timePlayedInOneLogin) {
        this.timePlayedInOneLogin = timePlayedInOneLogin;
    }
}
