import random
import time
import sys
import os

# Function to clear the screen
def clear_screen():
    os.system('cls' if os.name == 'nt' else 'clear')

# Function to draw the game screen
def draw_game(player_pos, asteroids, score):
    clear_screen()
    print("========= ASTEROID FIELD =========")
    print("Score:", score)
    print("       *")
    for i in range(5):
        line = "     "
        for j in range(10):
            if (i, j) in asteroids:
                line += "O"
            elif (i, j) == player_pos:
                line += "^"
            else:
                line += " "
        print(line)

# Function to move player left
def move_left(player_pos):
    x, y = player_pos
    return max(0, x), y

# Function to move player right
def move_right(player_pos):
    x, y = player_pos
    return min(4, x), y

# Function to update asteroid positions
def update_asteroids(asteroids):
    new_asteroids = set()
    for x, y in asteroids:
        if x < 4:
            new_asteroids.add((x+1, y))
    return new_asteroids

# Function to check collision
def check_collision(player_pos, asteroids):
    return player_pos in asteroids

# AI agent to control player movement
def ai_move(player_pos, asteroids):
    # Simple AI: Move towards the side with fewer asteroids
    left_count = sum(1 for x, y in asteroids if x == player_pos[0] and y < player_pos[1])
    right_count = sum(1 for x, y in asteroids if x == player_pos[0] and y > player_pos[1])
    if left_count < right_count:
        return move_left(player_pos)
    elif right_count < left_count:
        return move_right(player_pos)
    else:
        return player_pos

# Function to play the game automatically
def play_auto_game():
    player_pos = (4, 4)
    asteroids = set([(random.randint(0, 4), random.randint(0, 9)) for _ in range(5)])
    score = 0

    while True:
        draw_game(player_pos, asteroids, score)

        player_pos = ai_move(player_pos, asteroids)

        asteroids = update_asteroids(asteroids)
        if check_collision(player_pos, asteroids):
            print("Game Over! The AI crashed into an asteroid!")
            break

        score += 1
        time.sleep(0.1)

# Main function
def main():
    print("Welcome to the Asteroid Field!")
    print("Watch the AI-controlled spaceship navigate through the asteroid field!")
    input("Press Enter to start...")
    play_auto_game()
    print("Thanks for watching!")

if __name__ == "__main__":
    main()
