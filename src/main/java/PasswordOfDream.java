import java.util.Random;

public class PasswordOfDream {
    private static String chars;
    private static String digits;
    private static String simbols;

    static {
        chars = "A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z";
        digits = "0, 1, 2, 3, 4, 5, 6, 7, 8, 9";
        simbols = "!, #, $, %, &, *, +, -, /, :, ;, <, >, ?, @, [, ^, _, {, |, }, ~";
    }

    public String generatePassword(int length){
        Random random = new Random();
        String[] c = chars.split(", ");
        String[] d = digits.split(", ");
        String[] s = simbols.split(", ");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            double rand = random.nextDouble();
            if (rand < 0.5){
                int index = random.nextInt(c.length);
                builder.append(c[index]);
            }
            if (rand >= 0.5 && rand < 0.8){
                int index = random.nextInt(d.length);
                builder.append(d[index]);
            }
            if (rand >= 0.8) {
                int index = random.nextInt(s.length);
                builder.append(s[index]);
            }
        }
        return builder.toString();
    }
}
