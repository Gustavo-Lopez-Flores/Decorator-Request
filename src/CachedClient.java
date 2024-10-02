import java.util.HashMap;
import java.util.Map;

public class CachedClient extends DecoratorClient {
    private Map<String, Integer> cache = new HashMap<>();

    public CachedClient(ScoreClient client) {
        super(client);
    }

    @Override
    public int getScore(String cpf) {
        if (cache.containsKey(cpf)) {
            System.out.println("Retornando pontuação em cache para CPF: " + cpf);
            return cache.get(cpf);
        } else {
            int score = client.getScore(cpf);
            cache.put(cpf, score);
            return score;
        }
    }
}