# Contributing to Fitcloak

Thank you for your interest in contributing to Fitcloak!

## Getting Started

1. Fork the repository
2. Clone your fork: `git clone https://github.com/<your-username>/fitcloak.git`
3. Create a branch: `git checkout -b my-feature`
4. Copy config: `cp config.example.json config.json`
5. Download Keycloak themes: `./setup-keycloak-themes.sh`
6. Run: `./gradlew run`

## Development

### Prerequisites

- Java 17+
- Gradle 8+ (wrapper included)

### Building

```bash
./gradlew build
```

### Running Tests

```bash
./gradlew test
```

### Code Style

- Use 4 spaces for indentation
- Follow existing code patterns
- Add tests for new functionality
- Use SLF4J for logging (no `System.out.println`)

## Pull Requests

1. Ensure all tests pass: `./gradlew test`
2. Ensure the project builds: `./gradlew build`
3. Write a clear description of your changes
4. Reference any related issues

## Reporting Issues

- Use GitHub Issues
- Include steps to reproduce
- Include the Keycloak version you're targeting
- Include your Java version (`java -version`)

## License

By contributing, you agree that your contributions will be licensed under the MIT License.
