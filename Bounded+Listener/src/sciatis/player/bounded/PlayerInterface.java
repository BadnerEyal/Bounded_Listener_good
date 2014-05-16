package sciatis.player.bounded;


/**
 * This interface is used to expose from within the service
 * only the required methods for the activity
 * @author Gabriel@proto-mech.com
 *
 *���� ���� ���� ������ ��� �� ����� �����
 *��� ���� ����� �� ����� ����� ����� �� �� ��������� ���� �����
 *���� �� �������� ������ ������� ���� ����
 *�� ��� �� ����� ��� �� ������ ��� ���� ����� ���� ������ ���
 */
public interface PlayerInterface 
{
	public void startPlay(PlayerCompletionListener listener);
	public void stopPlay();
	
}
