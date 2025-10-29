# 🧮 Polynomial Coefficient Solver from JSON Roots

This project reads a set of polynomial roots from a JSON file, converts them from their respective bases to decimal, and computes the coefficients of the polynomial formed using those roots.

---

## 📂 Project Structure

```
Code
├── roots.json                   # Input file containing roots in various bases
├── PolynomialFromJsonFile.java # Main Java program
├── README.md                    # Project documentation
```

---

## 🚀 How It Works

- **Input Format**: Roots are provided in a JSON file with each root having a `base` and a `value`.
- **Conversion**: Each root is converted from its base to decimal.
- **Polynomial Construction**: Using the first `k` roots, the program constructs a polynomial of degree `k - 1`:

  

\[
  P(x) = (x - r_1)(x - r_2)...(x - r_k)
  \]



- **Output**: The coefficients of the expanded polynomial are printed in standard form.

---

## 📥 Sample Input (`roots.json`)

```json
{
  "keys": {
    "n": 4,
    "k": 3
  },
  "1": {
    "base": "10",
    "value": "4"
  },
  "2": {
    "base": "2",
    "value": "111"
  },
  "3": {
    "base": "10",
    "value": "12"
  },
  "6": {
    "base": "4",
    "value": "213"
  }
}
```

---

## 📤 Sample Output

```text
Polynomial Coefficients:
x^3: 1
x^2: -23
x^1: 136
x^0: -336
```

---

## 🛠️ Requirements

- Java 8 or higher
- `org.json` library for parsing JSON

---

## 📦 How to Run

1. Place `roots.json` in your project directory.
2. Compile and run the Java program:

```bash
javac PolynomialFromJsonFile.java
java PolynomialFromJsonFile
```

---

## 📌 Notes

- The program uses only the first `k` roots from the JSON file.
- Supports arbitrary base conversion using `BigInteger`.

---

## 🧠 Author

Created by **AYUSH** for a timed coding challenge.
