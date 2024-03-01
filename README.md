# Pair Guessing Game Overview 

This project presents a multiplayer Pair Guessing Game, akin to the classic Memory game. It leverages a client-server architecture where the server orchestrates the game, including managing tiles, a leaderboard, and logging, while clients connect to participate in the gameplay. Communication between server and clients is facilitated using the Protobuf protocol, detailed within a `.proto` file located in `src/main/proto`, the standard directory for such files.

## Project Features
- **Multiplayer Gameplay**: Allows multiple users to join a session and play the Pair Guessing Game together.
- **Protobuf Communication**: Utilizes Protobuf for efficient data serialization and message exchange between clients and the server.
- **Leaderboard Management**: Keeps track of player scores and displays a leaderboard, which persists across sessions and server restarts.
- **Game Persistence**: Ensures the game's state is maintained, allowing for seamless gameplay and leaderboard tracking.

## Running the Game
### Prerequisites
- Ensure Gradle is installed on your system.

### Setup and Execution
1. **Clone the Repository**: Obtain the project files by cloning this repository.
2. **Compile Protobuf Files**: Navigate to the project directory and compile the `.proto` files using:
   ```bash
   gradle generateProto
   ```
   This step is automatically handled when building the project.
3. **Start the Server**: Initiate the game server with:
   ```bash
   gradle runServer
   ```
4. **Launch a Client**: In a separate terminal, start a client instance to connect to the server:
   ```bash
   gradle runClient --args='ip_address port'
   ```
   Replace `ip_address` and `port` with the server's IP address and port number, respectively.

## Gameplay Instructions
- **Main Menu**: Upon connection, clients are presented with options to view the leaderboard, start a game, or exit.
- **In-Game**: Players input coordinates to flip tiles on the board, aiming to find matching pairs. Coordinates are entered as a two-character string (e.g., "A1").

## Development Highlights
- Implemented using Java, showcasing efficient client-server communication via Protobuf.
- Features a detailed leaderboard that tracks and displays player scores.
- Supports multiple simultaneous players, enhancing the competitive aspect.
- Designed to ensure server stability and robustness, even in the event of unexpected client disconnections.

## Additional Information
The game's infrastructure is built to be intuitive and user-friendly, promoting an engaging multiplayer experience. Through the use of Protobuf and a well-structured server-client protocol, the Pair Guessing Game stands as a model for developing networked multiplayer games.
 
 