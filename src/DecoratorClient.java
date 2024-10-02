public abstract class DecoratorClient implements ScoreClient {
    protected ScoreClient client;

    public DecoratorClient(ScoreClient client) {
        this.client = client;
    }

    @Override
    public int getScore(String cpf) {
        return client.getScore(cpf);
    }
}