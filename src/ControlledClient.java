public class ControlledClient extends DecoratorClient {
    private long lastRequestTime = 0;

    public ControlledClient(ScoreClient client) {
        super(client);
    }

    @Override
    public int getScore(String cpf) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastRequestTime < 1000) {
            try {
                Thread.sleep(1000 - (currentTime - lastRequestTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lastRequestTime = System.currentTimeMillis();
        return client.getScore(cpf);
    }
}