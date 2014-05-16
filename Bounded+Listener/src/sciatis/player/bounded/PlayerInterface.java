package sciatis.player.bounded;


/**
 * This interface is used to expose from within the service
 * only the required methods for the activity
 * @author Gabriel@proto-mech.com
 *
 *חוזה נממש אותו בשירות וכך מי שיבקש שירות
 *הוא חתום כאילו על החוזה ויכול לראות רק את הפונקציות שאנו רוצים
 *ניתן לו פונקציות להפעלה ולעצירה הנגן בלבד
 *כך הוא לא ישלוט לנו על השירות ולא יוכל לעצור אותו למישהו אחר
 */
public interface PlayerInterface 
{
	public void startPlay(PlayerCompletionListener listener);
	public void stopPlay();
	
}
