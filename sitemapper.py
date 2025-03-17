import os
import json
import threading
import sys

def map_directory(directory, depth=0):
    """Recursively maps the directory structure using threading with thread-safe console output, ignoring hidden directories."""
    structure = {}
    threads = []
    lock = threading.Lock()
    
    def process_entry(entry):
        nonlocal structure
        if entry.name.startswith("."):
            return  # Ignore hidden files and directories
        
        with lock:
            print(f"Mapping: {entry.path}", file=sys.stdout, flush=True)
        
        if entry.is_dir(follow_symlinks=False):
            result = map_directory(entry.path, depth + 1)
            with lock:
                structure[entry.name] = result
        else:
            with lock:
                structure[entry.name] = None  # Files have no further depth
    
    try:
        with os.scandir(directory) as entries:
            for entry in entries:
                if entry.name.startswith("."):
                    continue  # Skip hidden directories and files
                thread = threading.Thread(target=process_entry, args=(entry,))
                threads.append(thread)
                thread.start()
            
            for thread in threads:
                thread.join()
    except PermissionError:
        with lock:
            print(f"Permission Denied: {directory}", file=sys.stdout, flush=True)
        structure['ERROR'] = "Permission Denied"
    
    return structure

if __name__ == "__main__":
    root_directory = os.getcwd()  # Start from the current working directory
    sitemap = {os.path.basename(root_directory): map_directory(root_directory)}
    
    # Save the structure to a JSON file
    with open("sitemap.json", "w", encoding="utf-8") as json_file:
        json.dump(sitemap, json_file, indent=4)
    
    print("Sitemap has been saved to sitemap.json")
