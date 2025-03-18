import pygame
import sys
import random

# Initialize Pygame
pygame.init()

# Screen dimensions
SCREEN_WIDTH = 800
SCREEN_HEIGHT = 600

# Colors
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
BLUE = (0, 0, 255)
RED = (255, 0, 0)

# Player properties
PLAYER_SIZE = 40
PLAYER_SPEED = 5
PLAYER_HEALTH = 100

# NPC properties
NPC_SIZE = 40
NPC_POSITION = (200, 200)
NPC_HEALTH = 150

# Enemy properties
ENEMY_SIZE = 40
ENEMY_HEALTH = 10
ENEMY_DAMAGE_SWORD = 5
ENEMY_DAMAGE_GUN = 7
ENEMY_SPAWN_CHANCE = 0.25  # Chance of spawning an enemy with a gun

# Dialogue box properties
DIALOGUE_WIDTH = 600
DIALOGUE_HEIGHT = 100
DIALOGUE_COLOR = BLUE
DIALOGUE_MARGIN = 10
DIALOGUE_FONT_SIZE = 20

# Choices box properties
CHOICES_WIDTH = 200
CHOICES_HEIGHT = 150
CHOICES_COLOR = BLUE
CHOICES_MARGIN = 10
CHOICES_FONT_SIZE = 20

# Initialize screen
screen = pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGHT))
pygame.display.set_caption("Legend of Pygame")

# Font
font = pygame.font.Font(None, DIALOGUE_FONT_SIZE)
choices_font = pygame.font.Font(None, CHOICES_FONT_SIZE)

# Function to draw dialogue box
def draw_dialogue_box(text):
    pygame.draw.rect(screen, DIALOGUE_COLOR, (DIALOGUE_MARGIN, SCREEN_HEIGHT - DIALOGUE_HEIGHT - DIALOGUE_MARGIN, DIALOGUE_WIDTH, DIALOGUE_HEIGHT))
    text_surface = font.render(text, True, WHITE)
    screen.blit(text_surface, (DIALOGUE_MARGIN * 2, SCREEN_HEIGHT - DIALOGUE_HEIGHT + DIALOGUE_MARGIN))

# Function to draw choices box
def draw_choices_box(choices):
    pygame.draw.rect(screen, CHOICES_COLOR, (SCREEN_WIDTH - CHOICES_WIDTH - CHOICES_MARGIN, SCREEN_HEIGHT - CHOICES_HEIGHT - CHOICES_MARGIN, CHOICES_WIDTH, CHOICES_HEIGHT))
    for i, choice in enumerate(choices):
        choice_text = choices_font.render(choice, True, WHITE)
        screen.blit(choice_text, (SCREEN_WIDTH - CHOICES_WIDTH + CHOICES_MARGIN, SCREEN_HEIGHT - CHOICES_HEIGHT + CHOICES_MARGIN + i * (CHOICES_FONT_SIZE + 5)))

# Function to draw characters
def draw_characters(player_pos, npc_rect, enemies):
    pygame.draw.rect(screen, BLACK, (player_pos[0], player_pos[1], PLAYER_SIZE, PLAYER_SIZE))
    pygame.draw.rect(screen, BLUE, npc_rect)
    for enemy in enemies:
        pygame.draw.rect(screen, RED, enemy)

# Function to handle player movement
def handle_player_movement(player_pos, keys):
    if keys[pygame.K_w] or keys[pygame.K_UP]:
        player_pos[1] -= PLAYER_SPEED
    if keys[pygame.K_s] or keys[pygame.K_DOWN]:
        player_pos[1] += PLAYER_SPEED
    if keys[pygame.K_a] or keys[pygame.K_LEFT]:
        player_pos[0] -= PLAYER_SPEED
    if keys[pygame.K_d] or keys[pygame.K_RIGHT]:
        player_pos[0] += PLAYER_SPEED

# Function to handle player attack
def handle_player_attack(player_pos, enemies):
    for enemy in enemies:
        if enemy.colliderect(pygame.Rect(player_pos[0], player_pos[1], PLAYER_SIZE, PLAYER_SIZE)):
            enemies.remove(enemy)
            return True
    return False

# Function to spawn enemies
def spawn_enemies(enemies):
    if random.random() < ENEMY_SPAWN_CHANCE:
        # Spawn enemy with a gun
        enemy = pygame.Rect(random.choice([0, SCREEN_WIDTH - ENEMY_SIZE]), random.randint(0, SCREEN_HEIGHT - ENEMY_SIZE), ENEMY_SIZE, ENEMY_SIZE)
        enemies.append(enemy)
    else:
        # Spawn enemy with a sword
        enemy = pygame.Rect(random.choice([0, SCREEN_WIDTH - ENEMY_SIZE]), random.randint(0, SCREEN_HEIGHT - ENEMY_SIZE), ENEMY_SIZE, ENEMY_SIZE)
        enemies.append(enemy)

# Main game loop
def main():
    player_pos = [SCREEN_WIDTH // 2, SCREEN_HEIGHT // 2]
    npc_rect = pygame.Rect(NPC_POSITION[0], NPC_POSITION[1], NPC_SIZE, NPC_SIZE)
    dialogue = "Hello there! Press Space to talk."
    choices = ["Ask about the weather", "Ask about the village"]

    player_health = PLAYER_HEALTH
    npc_health = NPC_HEALTH

    enemies = []

    clock = pygame.time.Clock()

    while True:
        screen.fill(WHITE)

        keys = pygame.key.get_pressed()

        handle_player_movement(player_pos, keys)

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_SPACE:
                    if npc_rect.colliderect(pygame.Rect(player_pos[0], player_pos[1], PLAYER_SIZE, PLAYER_SIZE)):
                        dialogue = "Hello! I'm an NPC."
                        choices = ["Ask about the weather", "Ask about the village"]

        # Draw characters
        draw_characters(player_pos, npc_rect, enemies)

        # Draw dialogue box
        draw_dialogue_box(dialogue)

        # Draw choices box
        draw_choices_box(choices)

        # Handle player attack
        if keys[pygame.K_SPACE]:
            if handle_player_attack(player_pos, enemies):
                dialogue = "Enemy defeated!"
            else:
                dialogue = "No enemy nearby."

        # Spawn enemies
        if len(enemies) < 5:
            spawn_enemies(enemies)

        # Update display
        pygame.display.flip()

        # Cap the frame rate
        clock.tick(60)

# Run the game
if __name__ == "__main__":
    main()

