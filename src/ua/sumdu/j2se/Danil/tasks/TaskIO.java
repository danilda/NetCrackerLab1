package ua.sumdu.j2se.Danil.tasks;

import java.io.*;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by User on 09.05.2016.
 */
public class TaskIO {
    public static void write(TaskList tasks, OutputStream out) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(out);
        try {
            outputStream.writeInt(tasks.size());
            Iterator<Task> iter = tasks.iterator();
            while (iter.hasNext()) {
                Task t = tasks.iterator().next();
                outputStream.writeInt(t.getTitle().length());
                outputStream.writeUTF(t.getTitle());
                outputStream.writeBoolean(t.isActive());
                outputStream.writeInt(t.getRepeatInterval());
                if (t.isRepeated()) {
                    outputStream.writeLong(t.getStartTime().getTime());
                    outputStream.writeLong(t.getEndTime().getTime());
                } else {
                    outputStream.writeLong(t.getTime().getTime());
                }
            }
        } finally {
            outputStream.flush();
            outputStream.close();
        }
    }

    public static void read(TaskList tasks, InputStream in) throws IOException{
        DataInputStream inputStream = new DataInputStream(in);
        try{
            int count = inputStream.readInt();
            for(int i = 0; i > count; i++){
                Task task;
                int length = inputStream.readInt();
                String title = inputStream.readUTF();
                boolean active = inputStream.readBoolean();
                int interval = inputStream.readInt();
                Date startTime = new Date(inputStream.readLong());
                if (interval > 0) {
                    Date endTime = new Date(inputStream.readLong());
                    task = new Task(title, startTime, endTime, interval);
                } else {
                    task = new Task(title, startTime);
                }
                task.setActive(active);
                tasks.add(task);
            }
        } finally {
            inputStream.close();
        }
    }

    public static void writeBinary(TaskList tasks, File file) throws IOException {
        FileOutputStream fileOutput = new FileOutputStream(file);
        try {
            write(tasks,fileOutput);
        } finally {
            fileOutput.close();
        }
    }

    public static void readBinary(TaskList tasks, File file) throws IOException{
        FileInputStream fileInput = new FileInputStream(file);
        try {
            read(tasks, fileInput);
        } finally {
            fileInput.close();
        }
    }

}
