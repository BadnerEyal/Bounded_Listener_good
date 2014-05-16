package sciatis.player.bounded;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import android.view.View.OnClickListener;


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
 *
 *הדוגמא הנכונה כוללת חוזים
 *הפעלת שיר על ידי שירות 
 *בזמן עצירה או סיום השיר נקבל דיאלוג שישאל לנגן עוד פעם או לעזוב
 *
 */
public class PlayerActivity extends Activity implements PlayerCompletionListener
{
	private boolean isBounded = false;
	private PlayerInterface playerService = null;
	private PlayerServiceConnection playerServiceConnection = null;
	
	private class PlayerServiceConnection implements ServiceConnection
	{
		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			playerService = ((PlayerInterface)binder);
			isBounded = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			isBounded = false; 
		}
	}
	
	@Override
	protected void onStart() 
	{
		super.onStart();
		Intent playerServiceIntent = new Intent(this, ServicePlayer.class);
		bindService(playerServiceIntent, playerServiceConnection, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onStop() 
	{
		super.onStop();
		if(isBounded)
		{
			unbindService(playerServiceConnection);
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		playerServiceConnection = new PlayerServiceConnection();
		
		Button playerStartButton = (Button) findViewById(R.id.serviceStartButton);
		playerStartButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{

				if(isBounded && playerService != null)
				{
					playerService.startPlay(PlayerActivity.this);
				}
			}
		});
		
		Button playerStopButton = (Button) findViewById(R.id.serviceStopButton);
		playerStopButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{

				if(isBounded && playerService != null)
				{
					playerService.stopPlay();
				}
			}
		});
		
		
	}

	@Override
	public void onCompletion() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setTitle("Continue play?").setMessage("Are u sure u want to play again?");
		
		builder.setPositiveButton("Sure", new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				playerService.startPlay(PlayerActivity.this);
			}

		});
		builder.setNegativeButton("No ty", new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}

		});

		builder.create().show();
		
	}

	
	
	
	
	

}
