import youtube_dl
import pygame
import tkinter as tk

def play_audio(url):
    ydl_opts = {'quiet': True, 'format': 'bestaudio'}
    with youtube_dl.YoutubeDL(ydl_opts) as ydl:
        info = ydl.extract_info(url, download=False)
        audio_url = info['url']
        pygame.mixer.init()
        pygame.mixer.music.load(audio_url)
        pygame.mixer.music.play()

def main():
    root = tk.Tk()
    root.geometry('400x200')
    url_entry = tk.Entry(root)
    url_entry.pack()

    def play():
        url = url_entry.get()
        play_audio(url)

    play_button = tk.Button(root, text='Play Audio', command=play)
    play_button.pack()

    root.mainloop()

if __name__ == "__main__":
    main()
