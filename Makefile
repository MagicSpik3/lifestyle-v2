# Change this to match the name of your mod's jar file (without the version number)
PROJECT_NAME = modid

# This should be the version number defined in your gradle.properties file
VERSION = 1.0.0

# The path to your Minecraft mods folder
MODS_DIR = ~/.minecraft/mods

# The full path to the final JAR file after building
JAR_FILE = build/libs/$(PROJECT_NAME)-$(VERSION).jar

# The default command to run when you just type "make"
all: deploy

# Rule to build the project
build:
	@echo "Building the mod..."
	@./gradlew build

# Rule to copy the mod to the Minecraft mods folder.
# This rule depends on 'build', so it will automatically run the build first.
deploy: build
	@echo "Deploying $(JAR_FILE) to $(MODS_DIR)"
	@cp $(JAR_FILE) $(MODS_DIR)
	@echo "Deployment complete!"

# Rule to clean up the build files
clean:
	@echo "Cleaning up build files..."
	@rm -rf build
	@echo "Clean complete."

.PHONY: all build deploy clean