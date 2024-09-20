package src;
public class Main {
    public static void main(String[] args) {
        ScoreClient client = new CachedClient(new ControlledClient(new APIClient()));

        String[] cpfs = { "07527868147", "12345678909", "98765432100"};

        for (String cpf : cpfs) {
            System.out.println("Score for CPF " + cpf + ": " + client.getScore(cpf));
        }
    }
}