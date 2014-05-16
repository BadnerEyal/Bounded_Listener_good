package sciatis.player.bounded;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Binder;
import android.os.IBinder;

/**
 * This  example was created by Sciatis Technologies and belongs to.
 * 
 * Using this samples for teaching/training or distribution requires written approval from Sciatis Technologies.
 * 
 * Sciatis Technologies will not allow the use of this examples besides than development. 
 * 
 * For any questions please contact Gabriel@proto-mech.com
 * 
 * @author Gabriel@proto-mech.com
 */
public class ServicePlayer extends Service implements OnCompletionListener
{
	// יצרת מאזין משלנו כאשר יגמר השיר או הפונקציה פשוט נודיע למי שמאזין
	private PlayerCompletionListener listener = null;
	private MediaPlayer player = null;
	
	public ServicePlayer() {
		super();
	}

	
	private final LocalBinder binder = new LocalBinder();
	
	@Override
	public IBinder onBind(Intent intent) 
	{
		return binder;
	}
	
	@Override	
	public void onCreate() 
	{
		super.onCreate();
		initPlayer();
	}

	private void initPlayer() 
	{
		if(player != null)
		{
			if(player.isPlaying())
			{
				player.stop();
			}
			player.release();
			player = null;
		}
			
		player = MediaPlayer.create(this, R.raw.rihana_disturbia);
		player.setOnCompletionListener(this);
	}

	private void _startPlay() 
	{
	    if(!player.isPlaying())
		{
			player.start();
		}
	}
	
	private void _stopPlay() 
	{
		if(player != null)
		{
		   if (player.isPlaying()) 
		   {
			   player.stop();
		   }
			player.release();
		}
		
		player = null;
		initPlayer();
		listener.onCompletion();
		
	}
	
	//בזמן הריגת השירות נשחחר את הנגן
	@Override
	public void onDestroy() 
	{
		if(player != null)
		{
		   if (player.isPlaying()) 
		   {
			   player.stop();
		   }
		   player.release();
		   player = null;

		}
		super.onDestroy();
	}

	@Override
	public void onCompletion(MediaPlayer mp) 
	{
		//זה בעצם שליחת הטריגר שהשיר נגמר או נעצר
		//נפעיל את הפונקציה של המאזין
		//נאתחל את הנגן מחדש
		listener.onCompletion();
		initPlayer();
	}

	
	/**
	 * The returned binder will expose only the required methods
	 * for the activity alone(!). By that it will protect the service
	 * methods to be abused by the activity.
	 */
	// אינר קלאס בינאר הממש חוזה שכתבנו זה אומר מי שיבקש שירות
	// יקבל רק את הפונקציות האלו שאנו מאפשרים לו לירות
	public final class LocalBinder extends Binder implements PlayerInterface
	{
		@Override
		public void startPlay(PlayerCompletionListener listener) {
			ServicePlayer.this.listener = listener;
			_startPlay();
		}

		@Override
		public void stopPlay() {
			_stopPlay();
			
		}
	}
	
}





























		