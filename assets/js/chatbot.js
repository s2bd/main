document.addEventListener("DOMContentLoaded", async () => {
    const chatBox = document.getElementById("chatBox");
    const userInput = document.getElementById("userInput");
    const sendBtn = document.getElementById("sendBtn");

    let processedSitemap = {};

    // Greeting pool for initial bot response
    const greetings = [
        "Hey there! How can I assist you today?",
        "Hello! What can I help you with?",
        "Hi! How can I make your day easier?",
        "Hey, what can I do for you today?",
        "Greetings! What can I help you with?",
        "Hello! Need assistance with something?",
        "Hey, I’m here to help! What do you need?",
        "Hi there! How can I help?",
        "What’s up? How can I assist you today?",
        "Hello! What can I do for you?"
    ];

    // Message templates for bot replies
    const responseTemplates = [
    `Here's what I found: <a href="{url}" target="_blank">{url}</a>`,
    `You might be looking for this: <a href="{url}" target="_blank">{url}</a>`,
    `Check this out: <a href="{url}" target="_blank">{url}</a>`,
    `This could help: <a href="{url}" target="_blank">{url}</a>`,
    `I found this for you: <a href="{url}" target="_blank">{url}</a>`,
    `I think this is what you need: <a href="{url}" target="_blank">{url}</a>`,
    `Here’s a resource that may be useful: <a href="{url}" target="_blank">{url}</a>`,
    `Try this link: <a href="{url}" target="_blank">{url}</a>`,
    `This might be of interest: <a href="{url}" target="_blank">{url}</a>`,
    `You might find this helpful: <a href="{url}" target="_blank">{url}</a>`
];

    // Fetch and process sitemap
    try {
        const response = await fetch("/sitemap.json");
        const sitemapData = await response.json();
        processedSitemap = preprocessSitemap(sitemapData);
        console.log("✅ Processed Sitemap:", processedSitemap); // Debugging
    } catch (error) {
        console.error("❌ Failed to load sitemap:", error);
    }

    sendBtn.addEventListener("click", sendMessage);
    userInput.addEventListener("keypress", event => {
        if (event.key === "Enter") sendMessage();
    });

    // Send message when user clicks the send button or presses Enter
    function sendMessage() {
        const message = userInput.value.trim();
        if (!message) return;

        appendMessage(message, "user");
        userInput.value = "";

        setTimeout(() => {
            const response = generateResponse(message);
            appendMessage(response, "bot");
        }, 500);
    }

    // Append message (with word-by-word animation for bot)
    function appendMessage(text, sender) {
        const messageElement = document.createElement("div");
        messageElement.classList.add("chat-message", sender === "user" ? "user-message" : "bot-message");

        if (sender === "bot") {
            const img = document.createElement("img");
            img.src = "/img/mux/pink-gradient.png";
            messageElement.appendChild(img);
        }

        const textDiv = document.createElement("div");
        messageElement.appendChild(textDiv);
        chatBox.appendChild(messageElement);
        chatBox.scrollTop = chatBox.scrollHeight;

        // For bot messages, ensure HTML links are rendered correctly
        if (sender === "bot") {
            // Animate the text word-by-word, but ensure HTML tags like <a> remain intact
            animateText(textDiv, text);
        } else {
            textDiv.textContent = text; // For user messages, plain text is fine
        }
    }

    // Animate text one word at a time
    function animateText(element, text) {
    const tempDiv = document.createElement("div");
    tempDiv.innerHTML = text; // Parse HTML properly

    const words = [];
    tempDiv.childNodes.forEach(node => {
        if (node.nodeType === Node.TEXT_NODE) {
            words.push(...node.textContent.split(" ")); // Split text nodes into words
        } else {
            words.push(node.outerHTML); // Keep HTML elements (like links) intact as one unit
        }
    });

    let index = 0;

    function typeNextWord() {
        if (index < words.length) {
            element.innerHTML += words[index] + " "; // Add word or entire HTML element
            index++;
            setTimeout(typeNextWord, 100); // Adjust typing speed here
        }
    }

    typeNextWord();
}


    function cleanString(str) {
        return str.replace(/[^a-zA-Z0-9]/g, "").toLowerCase();
    }

    // Preprocess sitemap data for easy matching
    function preprocessSitemap(data, currentPath = "/") {
        let flattened = {};

        function traverse(obj, path) {
            for (const key in obj) {
                if (!obj.hasOwnProperty(key)) continue;

                const newPath = `${path}/${key}`.replace("//", "/").replace("/index.html", "");
                const cleanedKey = cleanString(key);
                const searchableKey = cleanString(newPath.replace("/main", "")); // Keep paths searchable

                if (typeof obj[key] === "object" && obj[key] !== null) {
                    traverse(obj[key], newPath);
                } else {
                    let finalPath = newPath.replace("/main", ""); // Fix extra /main/main issue
                    flattened[searchableKey] = finalPath;
                    flattened[cleanedKey] = finalPath; // Allow simpler lookups
                }
            }
        }

        traverse(data, "/");
        return flattened;
    }

    // Find best match based on query
    function findBestMatch(query) {
        const cleanedQuery = cleanString(query);
        let bestMatch = null;
        let highestScore = 0;

        for (const key in processedSitemap) {
            let score = matchScore(cleanedQuery, key);
            if (score > highestScore) {
                highestScore = score;
                bestMatch = processedSitemap[key];
            }
        }

        console.log(`🧐 User Query: "${query}" → Best Match: "${bestMatch}"`); // Debugging

        return bestMatch;
    }

    // Calculate match score for query
    function matchScore(query, key) {
        if (query === key) return 100; // Exact match
        if (key.includes(query)) return query.length * 2; // Partial match

        let score = 0;
        if (query.split("").every(char => key.includes(char))) score += 10; // Loose match

        return score;
    }

    // Generate response for the user
    function generateResponse(userMessage) {
    const bestMatch = findBestMatch(userMessage);
    if (!bestMatch) return "Sorry, I couldn't find that resource.";

    let fullUrl = `https://mux8.com${bestMatch}`;

    // Select a random response template
    const randomTemplate = responseTemplates[Math.floor(Math.random() * responseTemplates.length)];

    // Replace **all** occurrences of `{url}`
    const formattedResponse = randomTemplate.replaceAll("{url}", fullUrl);

    return formattedResponse;
}

    // Send a random greeting when the page loads (if it's the first message)
    setTimeout(() => {
        appendMessage(greetings[Math.floor(Math.random() * greetings.length)], "bot");
    }, 1000); // Delay to simulate bot thinking

// Function to insert an intro message at the start
function insertIntroMessage() {
    const introMessage = document.createElement("div");
    introMessage.classList.add("intro-message");
    introMessage.textContent = "Welcome to the Mux Web Presence. This chatbot is here to help you find resources using natural language rather than having to type in a search bar. At the moment, the chatbot has very limited knowledge and can only respond to messages with the closest match of the webpage/file you're looking for.";
    
    chatBox.appendChild(introMessage);
}

// Insert intro message on page load
insertIntroMessage();
  
});
