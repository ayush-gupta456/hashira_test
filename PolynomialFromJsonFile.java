import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Removed dependency on org.json — use a small regex-based parser for the project's simple JSON

public class PolynomialFromJsonFile {
    public static void main(String[] args) {
        try {
            // Read file contents
            String content = new String(Files.readAllBytes(Paths.get("roots.json")), StandardCharsets.UTF_8);

            // Extract k from the "keys" object: "k": <number>
            Pattern kPattern = Pattern.compile("\"k\"\s*:\s*(\\d+)");
            Matcher kMatcher = kPattern.matcher(content);
            int k = -1;
            if (kMatcher.find()) {
                k = Integer.parseInt(kMatcher.group(1));
            } else {
                throw new IllegalStateException("Could not find 'k' in roots.json");
            }

            // Extract numeric entries like "1": { "base": "10", "value": "4" }
            Pattern entryPattern = Pattern.compile("\"(\\d+)\"\s*:\s*\\{\\s*\"base\"\s*:\s*\"(\\d+)\"\s*,\\s*\"value\"\s*:\s*\"([^\"]+)\"\\s*\\}", Pattern.MULTILINE);
            Matcher entryMatcher = entryPattern.matcher(content);
            Map<Integer, String[]> entries = new HashMap<>();
            while (entryMatcher.find()) {
                int idx = Integer.parseInt(entryMatcher.group(1));
                String baseStr = entryMatcher.group(2);
                String value = entryMatcher.group(3);
                entries.put(idx, new String[] { baseStr, value });
            }

            List<BigInteger> roots = new ArrayList<>();
            for (int i = 1; roots.size() < k; i++) {
                if (!entries.containsKey(i)) continue;
                String[] pair = entries.get(i);
                int base = Integer.parseInt(pair[0]);
                String value = pair[1];
                BigInteger root = new BigInteger(value, base);
                roots.add(root);
            }

            List<BigInteger> coefficients = computePolynomialCoefficients(roots);
            System.out.println("Polynomial Coefficients:");
            for (int i = 0; i < coefficients.size(); i++) {
                System.out.println("x^" + (coefficients.size() - 1 - i) + ": " + coefficients.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<BigInteger> computePolynomialCoefficients(List<BigInteger> roots) {
        List<BigInteger> coeffs = new ArrayList<>();
        coeffs.add(BigInteger.ONE); // Start with P(x) = 1

        for (BigInteger root : roots) {
            List<BigInteger> newCoeffs = new ArrayList<>();
            newCoeffs.add(coeffs.get(0).negate().multiply(root)); // constant term

            for (int i = 1; i < coeffs.size(); i++) {
                BigInteger term = coeffs.get(i - 1).subtract(coeffs.get(i).multiply(root));
                newCoeffs.add(term);
            }

            newCoeffs.add(coeffs.get(coeffs.size() - 1)); // highest degree term
            coeffs = newCoeffs;
        }

        Collections.reverse(coeffs);
        return coeffs;
    }
}
