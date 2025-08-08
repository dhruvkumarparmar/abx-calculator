# ab^x Calculator — SOEN 6011 D3 (F5)

**Version:** 1.0.0 (Semantic Versioning)

Java Swing application to compute **a · b^x** without using `Math.pow()`. Includes:
- Accessible GUI (labels, mnemonics, tab order, accessible names)
- From-scratch math (`CustomMath.exp`, `CustomMath.ln`)
- Custom exception hierarchy
- Checkstyle & PMD setup
- JUnit 5 tests

## Run (no IDE required)

```bash
# Compile
javac -d out $(find src/main/java -name "*.java")

# Run
java -cp out com.dhruv.abx.SmartExponentialCalculator
```

## With Maven (recommended for tests & reports)

```bash
mvn -q -e -DskipTests=false test
mvn -q -DskipTests=false verify   # runs Checkstyle + PMD
mvn -q exec:java -Dexec.mainClass="com.dhruv.abx.SmartExponentialCalculator"
```

> If `exec:java` is not configured, run with plain `java -cp` as above.

## Screenshots to Include (D3)

- **Checkstyle report** after `mvn verify`
- **PMD report** after `mvn verify`
- **Debugger** (IntelliJ debugger or `jdb`) showing a breakpoint in `AbxCalculator.compute`
- **Accessibility**: screenshot of GUI with focus outlines and screen reader labels

## Testing

```bash
mvn -q test
```

Key tests in `AbxCalculatorTest` cover:
- Simple integers, base 1, negative exponents, fractional base
- Invalid base (≤0) raising exceptions
- Overflow case

## Style

Configured to **Google Java Style** via Checkstyle.

## Notes

- `CustomMath` implements simple series for `exp` and `ln`. For extreme values, accuracy may degrade; the app guards over/underflow and displays helpful messages.
- For SonarLint, install the plugin in IntelliJ; capture a screenshot of detected issues (if any).

## Author
Dhruvkumar Parmar — SOEN 6011 — Function F5
