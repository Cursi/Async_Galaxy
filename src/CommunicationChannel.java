import java.util.Hashtable;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Class that implements the channel used by headquarters and space explorers to communicate.
 */
public class CommunicationChannel
{
    public static Hashtable<Integer, ConcurrentLinkedQueue<Message>> headQuartersHashTable = new Hashtable<>();

    public static ConcurrentLinkedQueue<Message> spaceExplorersMessages = new ConcurrentLinkedQueue<>();
    public static ConcurrentLinkedQueue<Message> headQuartersMessages = new ConcurrentLinkedQueue<>();

	/**
	 * Creates a {@code CommunicationChannel} object.
	 */
	public CommunicationChannel()
	{
	}

	/**
	 * Puts a message on the space explorer channel (i.e., where space explorers write to and 
	 * headquarters read from).
	 * 
	 * @param message
	 *            message to be put on the channel
	 */
	public void putMessageSpaceExplorerChannel(Message message)
    {
		this.spaceExplorersMessages.add(message);
	}

	/**
	 * Gets a message from the space explorer channel (i.e., where space explorers write to and
	 * headquarters read from).
	 * 
	 * @return message from the space explorer channel
	 */
	public Message getMessageSpaceExplorerChannel()
    {
		return this.spaceExplorersMessages.poll();
	}

	/**
	 * Puts a message on the headquarters channel (i.e., where headquarters write to and 
	 * space explorers read from).
	 * 
	 * @param message
	 *            message to be put on the channel
	 */
	public void putMessageHeadQuarterChannel(Message message)
    {
        Integer currentHeadQuarterID = Math.toIntExact(HeadQuarter.currentThread().getId());

        if(!"EXIT".equals(message.getData()))
        {
            if("END".equals(message.getData()))
            {
                while(!this.headQuartersHashTable.get(currentHeadQuarterID).isEmpty())
                {
                    Message currentParentMessage = this.headQuartersHashTable.get(currentHeadQuarterID).poll();
                    Message currentChildMessage = this.headQuartersHashTable.get(currentHeadQuarterID).poll();

                    Message toBeSentMessage = new Message
                    (
                        currentParentMessage.getCurrentSolarSystem(),
                        currentChildMessage.getCurrentSolarSystem(),
                        currentChildMessage.getData()
                    );

                    this.headQuartersMessages.add(toBeSentMessage);
                }
            }
            else
            {
                if(this.headQuartersHashTable.get(currentHeadQuarterID) == null)
                    this.headQuartersHashTable.put(currentHeadQuarterID, new ConcurrentLinkedQueue<Message>());

                this.headQuartersHashTable.get(currentHeadQuarterID).add(message);
            }
        }
    }

	/**
	 * Gets a message from the headquarters channel (i.e., where headquarters write to and
	 * space explorer read from).
	 * 
	 * @return message from the header quarter channel
	 */
	public Message getMessageHeadQuarterChannel()
    {
    	return this.headQuartersMessages.poll();
	}
}