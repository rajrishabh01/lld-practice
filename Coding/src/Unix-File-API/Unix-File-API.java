/*
* API - Search the File System
*
* Searching File based on Name, Extension, Size
*
* Input for search (directory)
*
* */

import java.util.*;

public class Main{
    public static void main (String[] args){
        // Create Files with name, extensions and size
        File file1 = new File("file1", "java", 10);
        ..
        // Add to Directory
        Directory root = new Directory("/", true);
        root.addFile(file1);
        ..
        // Setup Filters - in Maps
        Map<String, Object> filter = new HashMap<>();
        filter.put("NameFilter", "file1");
        filter.put("ExtensionFilter", "java");
        // Use Filter map in Search
        Search search = new Search(filter, root, "OR");
        List<File> results = search.findFiles();
        // Print results
        System.out.println(Arrays.stream(results));

    }
}
//--------------------------------------------------------------------------------------------------------------------------------
// Main objects .java
public class File {
    private String name;
    private String extension;
    private int size;

    public File(name, extension, size){
        this.name ....
    }

    //setters and getters
    ...
}

public class Directory {
    private String path;
    private Boolean isDirectory;
    private List<File> files;
    private List<Directory> subDirectories;
    //setters and getters
    ...
    public class void addFiles(List<File> files){
        ...
    }

    public class void addFile(File file){
        ...
    }

    public class void addSubDirectories(List<Directory> subDirectories){
        ...
    }
}
//--------------------------------------------------------------------------------------------------------------------------------
// Filters
public abstract class Filter {
    public abstract boolean match(File file);
}

public class NameFilter extends Filter {
    String nameValue;
    File fileGiven;

    public NameFilter(String nameToMatch){
        this.nameValue = nameToMatch;
    }

    @Override
    public boolean match(File file){
        return file.getName().equals(this.nameValue);
    }
}

public class ExtensionFilter extends Filter {
    //.. same for extension
}

public class SizeFilter extends Filter {
    //.. same for size
    int sizeOperator;
    int sizeToCompare;

    @Override
    public boolean match(File file){
        switch(this.sizeOperator){
            case ">":
                return file.getSize() > sizeToCompare; //....
        }

    }
}
//--------------------------------------------------------------------------------------------------------------------------------
// Search
public class Search {
    List<Filter> filters;
    Directory directoryToSearch;
    String conditions;

    public Search(Map<String, Object> filters, Directory directory, String conditions){
        this.conditions = conditions;
        this.directoryToSearch = directory;

        for(Entry entry: filters.entrySet()){
            if(entry.getKey() == "NameFilter"){
                this.filters(new NameFilter(entry.getValue()));
            }
            // ..
        }
    }

    //Find Files with BFS - queue
    public List<File> findFiles(){
        Queue<Directory> directoryToSearch = new LinkedList<>();
        List<File> results = new ArrayList<>();

        while(!directoryToSearch.isEmpty()){
            Directory current = directoryToSearch.poll();
            for(File file: current.getFiles()){
                if(checkConditions(file)) {
                    results.add(file);
                }
            }

            for(Directory subDirectories: current.getSubDirectories()){
                directoryToSearch.offer(subDirectories);
            }
        }

        return results;
    }

    private boolean checkConditions(File file){
        if(conditions == "OR") {
            for (Filter filter : filters) {
                if (filter.match(file)) {
                    return true;
                }
            }
        }
       else if(condition == "AND"){ // different for AND -
            for(Filter filter:filters) {
                if (!filter.match(file)) {
                    return false;
                }
            }
    }
}

