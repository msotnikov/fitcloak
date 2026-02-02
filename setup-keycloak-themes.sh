#!/bin/bash
set -e

# Fitcloak - Keycloak Base Themes Downloader
# Downloads Keycloak base themes required for theme inheritance

DEFAULT_BRANCH="main"
DEFAULT_REPO="https://github.com/keycloak/keycloak.git"
TARGET_DIR="keycloak/base"

print_usage() {
  echo "Usage: ./setup-keycloak-themes.sh [branch|tag] [repo-url]"
  echo ""
  echo "Downloads Keycloak base themes for use with Fitcloak."
  echo ""
  echo "Arguments:"
  echo "  branch|tag   Git branch or tag to download (default: main)"
  echo "               Examples: main, archive/release/23.0, archive/release/24.0"
  echo "  repo-url     Git repository URL (default: keycloak/keycloak)"
  echo ""
  echo "Examples:"
  echo "  ./setup-keycloak-themes.sh                        # Latest (main branch)"
  echo "  ./setup-keycloak-themes.sh archive/release/23.0   # Keycloak 23"
  echo "  ./setup-keycloak-themes.sh archive/release/24.0   # Keycloak 24"
}

if [ "$1" = "--help" ] || [ "$1" = "-h" ]; then
  print_usage
  exit 0
fi

BRANCH="${1:-$DEFAULT_BRANCH}"
REPO="${2:-$DEFAULT_REPO}"

echo "========================================="
echo "  Fitcloak - Keycloak Themes Downloader"
echo "========================================="
echo ""
echo "  Branch: $BRANCH"
echo "  Repo:   $REPO"
echo "  Target: $TARGET_DIR"
echo ""

# Clean up existing download
if [ -d "$TARGET_DIR" ]; then
  echo "Removing existing $TARGET_DIR..."
  rm -rf "$TARGET_DIR"
fi

mkdir -p "$TARGET_DIR"
cd "$TARGET_DIR"

# Initialize git with sparse checkout
git init -q
git remote add origin "$REPO"

# Configure sparse checkout to only get themes
git sparse-checkout init --cone
git sparse-checkout set themes/src/main

# Fetch and checkout
echo "Fetching themes from $BRANCH..."
git fetch --depth=1 origin "$BRANCH"
git checkout FETCH_HEAD -q

# --- Download PatternFly vendor assets ---
# Keycloak's js/themes-vendor/pom.xml copies these from node_modules.
# We read package.json from the same commit to get exact versions,
# install them in a temp dir, and copy to the right places.

VENDOR_PKG=$(git show HEAD:js/themes-vendor/package.json 2>/dev/null || true)

if [ -z "$VENDOR_PKG" ]; then
  echo ""
  echo "Warning: js/themes-vendor/package.json not found in this branch."
  echo "Skipping vendor asset download. PatternFly CSS will not be available."
  echo ""
else
  echo "Downloading PatternFly vendor assets..."

  COMMON_DIR="themes/src/main/resources/theme/keycloak/common/resources"
  TMPDIR_VENDOR=$(mktemp -d)

  # Write a minimal package.json with only the CSS dependencies we need
  # Extract versions from the Keycloak commit's package.json
  PF3_VER=$(echo "$VENDOR_PKG" | grep '"patternfly"' | head -1 | sed 's/.*: *"\(.*\)".*/\1/')
  PF4_VER=$(echo "$VENDOR_PKG" | grep '"@patternfly/patternfly"' | head -1 | sed 's/.*: *"\(.*\)".*/\1/')
  PF5_SPEC=$(echo "$VENDOR_PKG" | grep '"@patternfly-v5/patternfly"' | head -1 | sed 's/.*: *"\(.*\)".*/\1/')

  cat > "$TMPDIR_VENDOR/package.json" <<PKGJSON
{
  "name": "fitcloak-vendor-tmp",
  "private": true,
  "dependencies": {
    "patternfly": "${PF3_VER:-^3.59.5}",
    "@patternfly/patternfly": "${PF4_VER:-^4.224.5}"$([ -n "$PF5_SPEC" ] && echo ",
    \"@patternfly-v5/patternfly\": \"$PF5_SPEC\"")
  }
}
PKGJSON

  echo "  Installing PatternFly packages (versions from Keycloak $BRANCH)..."
  (cd "$TMPDIR_VENDOR" && npm install --no-audit --no-fund --loglevel=error 2>&1) || {
    echo "Warning: npm install failed. Skipping vendor assets."
    rm -rf "$TMPDIR_VENDOR"
    TMPDIR_VENDOR=""
  }

  if [ -n "$TMPDIR_VENDOR" ] && [ -d "$TMPDIR_VENDOR/node_modules" ]; then
    NM="$TMPDIR_VENDOR/node_modules"

    # PatternFly v3: css/, fonts/, img/
    if [ -d "$NM/patternfly/dist" ]; then
      mkdir -p "$COMMON_DIR/vendor/patternfly-v3"
      cp -r "$NM/patternfly/dist/css" "$COMMON_DIR/vendor/patternfly-v3/"
      cp -r "$NM/patternfly/dist/fonts" "$COMMON_DIR/vendor/patternfly-v3/"
      [ -d "$NM/patternfly/dist/img" ] && cp -r "$NM/patternfly/dist/img" "$COMMON_DIR/vendor/patternfly-v3/"
      echo "  PatternFly v3 ✓"
    fi

    # PatternFly v4: patternfly.min.css, assets/
    if [ -d "$NM/@patternfly/patternfly" ]; then
      mkdir -p "$COMMON_DIR/vendor/patternfly-v4"
      cp "$NM/@patternfly/patternfly/patternfly.min.css" "$COMMON_DIR/vendor/patternfly-v4/"
      [ -d "$NM/@patternfly/patternfly/assets" ] && cp -r "$NM/@patternfly/patternfly/assets" "$COMMON_DIR/vendor/patternfly-v4/"
      echo "  PatternFly v4 ✓"
    fi

    # PatternFly v5 (if present in this Keycloak version)
    if [ -d "$NM/@patternfly-v5/patternfly" ]; then
      mkdir -p "$COMMON_DIR/vendor/patternfly-v5"
      cp "$NM/@patternfly-v5/patternfly/patternfly.min.css" "$COMMON_DIR/vendor/patternfly-v5/"
      [ -f "$NM/@patternfly-v5/patternfly/patternfly-addons.css" ] && cp "$NM/@patternfly-v5/patternfly/patternfly-addons.css" "$COMMON_DIR/vendor/patternfly-v5/"
      [ -d "$NM/@patternfly-v5/patternfly/assets" ] && cp -r "$NM/@patternfly-v5/patternfly/assets" "$COMMON_DIR/vendor/patternfly-v5/"
      echo "  PatternFly v5 ✓"
    fi

    rm -rf "$TMPDIR_VENDOR"
  fi
fi

echo ""
echo "Done! Keycloak themes downloaded to $TARGET_DIR/themes/src/main/resources/theme"
echo ""
echo "Make sure your config.json has:"
echo "  \"keycloakThemesPath\": \"$TARGET_DIR/themes/src/main/resources/theme\""
