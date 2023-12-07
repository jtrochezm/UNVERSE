import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Random;

class Schedule {
    Scanner scanner = new Scanner(System.in);
    int numberOfDays;
    HashLinkedList[] schedule;

    public Schedule() {
        this.numberOfDays = 8;
        schedule = new HashLinkedList[8];
    }

    public void createNewClase() {
        System.out.println(
                "Ingrese el día de la clase: \n Lunes=1 Martes=2 Miercoles=3 Jueves=4 Viernes=5 Sabado=6 Domingo=7");
        int day = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ingrese el nombre de la clase: ");
        String name = scanner.nextLine();
        System.out.println("Ingrese la hora de inicio de la clase");
        int startTime = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ingrese la hora de finalizacion de la clase");
        int endTime = scanner.nextInt();
        scanner.nextLine();
        newClase(day, name, startTime, endTime);

    }

    public void newClase(int day, String name, int startTime, int endTime) {
        if (schedule[day] == null) {
            schedule[day] = new HashLinkedList();
        }
        if (schedule[day].insert(name, startTime, endTime)) {
            //System.out.println("Actividad insertada.");
        } else {
            System.out.println("Actividad no insertada.");
        }
        ;
    }

    public void createUpdateClase() {
        int option;
        System.out.println(
                "Ingrese el día de la clase: \n Lunes=1 Martes=2 Miercoles=3 Jueves=4 Viernes=5 Sabado=6 Domingo=7");
        int day = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ingrese el indice de la clase: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        System.out.println(
                "\n Actualizar: \n 1.Nombre de la clase  \n 2.Hora de inicio de la clase \n 3.Hora de finalizacion de la clase");
        System.out.print("Ingrese su opción: ");
        option = scanner.nextInt();

        switch (option) {
            case 1:
                updateClaseName(day, index);
                break;
            case 2:
                updateClaseStartTime(day, index);
                break;
            case 3:
                updateClaseEndTime(day, index);
                break;
            default:
                System.out.println("Opción no válida. Actividad no insertada.");
        }
    }

    public void updateClaseName(int day, int index) {
        if (schedule[day] != null) {
            System.out.println();
            System.out.println("Ingrese el nuevo nombre de la clase: ");
            String name = scanner.nextLine();
            schedule[day].updateName(name, index);
        } else {
            System.out.println("El dia " + day + " no tiene clases.");
        }
    }

    public void updateClaseStartTime(int day, int index) {
        if (schedule[day] != null) {
            System.out.println("Ingrese la nueva hora de inicio de la clase: ");
            int startTime = scanner.nextInt();
            scanner.nextLine();
            schedule[day].updateStartTime(startTime, index);
        } else {
            System.out.println("El dia " + day + " no tiene clases.");
        }
    }

    public void updateClaseEndTime(int day, int index) {
        if (schedule[day] != null) {
            System.out.println("Ingrese la nueva hora de finalizacion de la clase: ");
            int endTime = scanner.nextInt();
            scanner.nextLine();
            schedule[day].updateEndTime(endTime, index);
        } else {
            System.out.println("El dia " + day + " no tiene clases.");
        }
    }

    public void createDeleteClase() {
        System.out.println("Ingrese el día de la clase: ");
        int day = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ingrese el indice de la clase: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        deleteClase(day, index);
    }

    public void deleteClase(int day, int index) {
        if (schedule[day] != null) {
            if (schedule[day].delete(index)) {
                //System.out.println("Actividad eliminada.");
            } else {
                System.out.println("Actividad no eliminada.");
            }
            ;
            if (schedule[day].isEmpty()) {
                schedule[day] = null;
            }
        } else {
            System.out.println("El dia " + day + " no tiene clases.");
        }
    }

    public void createSearchClase() {
        System.out.println("Ingrese el día de la clase ");
        int day = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ingrese el indice de la clase: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        searchClase(day, index);
    }

    public void searchClase(int day, int index) {
        if (schedule[day] != null) {
            schedule[day].search(day, index);
        } else {
            System.out.println("El dia " + day + " no tiene clases.");
        }
    }

    public void createShowDay() {
        System.out.println(
                "Ingrese el día que quiere ver: \n Lunes=1 Martes=2 Miercoles=3 Jueves=4 Viernes=5 Sabado=6 Domingo=7 ");
        int day = scanner.nextInt();
        scanner.nextLine();
        showDay(day);
    }

    public void showDay(int day) {
        if (schedule[day] != null) {
            schedule[day].printRecursive(day);
        } else {
            System.out.println("El dia " + day + " no tiene clases.");
        }
    }

    public void showSchedule() {
        for (int i = 1; i < numberOfDays; i++) {
            if (schedule[i] != null) {
                schedule[i].printRecursive(i);
            } else {
                System.out.println();
                System.out.println("El dia " + i + " no tiene clases.");
            }
        }
    }

    public void exportSchedule() {
        System.out.println("Ingrese el nombre del archivo de exportación: ");
        String archivo = scanner.nextLine();

        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            for (int i = 0; i < numberOfDays; i++) {
                if (schedule[i] != null) {
                    writer.println(schedule[i].recursiveExport(i));
                } else {
                    writer.println("El dia " + i + " no tiene tareas.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMenu() {
        int option;
        System.out.println("Menú:");
        System.out.println("1. Nueva clase");
        System.out.println("2. Actualizar clase");
        System.out.println("3. Eliminar clase");
        System.out.println("4. Buscar clase");
        System.out.println("5. Mostrar día");
        System.out.println("6. Mostrar horario");
        System.out.println("7. Exportar horario");
        System.out.println("8. Salir");
        do {
            System.out.print("Ingrese su opción: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    createNewClase();
                    break;
                case 2:
                    createUpdateClase();
                    break;
                case 3:
                    createDeleteClase();
                    break;
                case 4:
                    createSearchClase();
                    break;
                case 5:
                    createShowDay();
                    break;
                case 6:
                    showSchedule();
                    break;
                case 7:
                    exportSchedule();
                    break;
                case 8:
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }

        } while (option != 8);

    }

    class HashLinkedList {
        private Node head;

        public HashLinkedList() {
            head = null;
        }

        public boolean isEmpty() {
            Node ptr;
            ptr = head;
            if (ptr == null) {
                return true;
            } else {
                return false;
            }
        }

        public boolean insert(String name, int startTime, int endTime) {
            int index = 0;
            boolean inserted;
            Node ptr, prev;
            inserted = false;
            ptr = head;
            prev = null;
            while (ptr != null) {
                prev = ptr;
                ptr = ptr.getNext();
            }
            if (ptr == null) {
                inserted = true;
                if (prev == null) {
                    index = 1;
                } else {
                    index = prev.getIndex() + 1;
                }
                Node newp = new Node(name, startTime, endTime, index);
                newp.setNext(ptr);
                if (prev == null)
                    head = newp;
                else
                    prev.setNext(newp);
            }
            return inserted;
        }

        public boolean updateName(String name, int index) {
            boolean updated = false;
            Node ptr;
            ptr = head;
            while (ptr != null && ptr.getIndex() != index) {
                ptr = ptr.getNext();
                System.out.println(ptr.getIndex());
            }
            if (ptr != null) {
                ptr.setName(name);
                updated = true;
            }
            return updated;
        }

        public boolean updateStartTime(int startTime, int index) {
            boolean updated = false;
            Node ptr;
            ptr = head;
            while (ptr != null && ptr.getIndex() != index) {
                ptr = ptr.getNext();
                System.out.println(ptr.getIndex());
            }
            if (ptr != null) {
                ptr.setStartTime(startTime);
                updated = true;
            }
            return updated;
        }

        public boolean updateEndTime(int endTime, int index) {
            boolean updated = false;
            Node ptr;
            ptr = head;
            while (ptr != null && ptr.getIndex() != index) {
                ptr = ptr.getNext();
                System.out.println(ptr.getIndex());
            }
            if (ptr != null) {
                ptr.setEndTime(endTime);
                updated = true;
            }
            return updated;
        }

        public boolean delete(int index) {
            boolean deleted = false;
            Node ptr, prev;
            ptr = head;
            prev = null;
            while (ptr != null && ptr.getIndex() != index) {
                prev = ptr;
                ptr = ptr.getNext();
            }
            if (ptr != null) {
                deleted = true;
                if (prev == null)
                    head = ptr.getNext();
                else
                    prev.setNext(ptr.getNext());
            }
            Node current = ptr.getNext();
            while (current != null) {
                current.setIndex(current.getIndex() - 1);
                current = current.getNext();
            }
            return deleted;
        }

        public boolean search(int day, int index) {
            boolean found = false;
            Node ptr;
            ptr = head;
            while (ptr != null && ptr.getIndex() != index) {
                ptr = ptr.getNext();
            }
            if (ptr != null) {
                found = true;
                System.out.println("\nClase encontrada: ");
                System.out.println("Dia " + day);
                System.out.print("Index de la clase: ");
                System.out.println(ptr.getIndex());
                System.out.print("Nombre de la clase: ");
                System.out.println(ptr.getName());
                System.out.print("Hora de inicio: ");
                System.out.println(ptr.getStartTime());
                System.out.print("Hora finalizacion: ");
                System.out.println(ptr.getEndTime());
            }
            return found;
        }

        public void printRecursive(int day) {
            System.out.println();
            System.out.print("Dia " + day + ":");
            System.out.println();
            printR(head);
            System.out.println();
        }

        public String recursiveExport(int day) {
            String dayExport = "El dia " + day + ":";
            String dayTasks = recursiveExportAux(head);
            if (!dayTasks.isEmpty()) {
                dayExport += " " + dayTasks;
            }
            return dayExport;
        }

        private String recursiveExportAux(Node p) {
            String dayTasks = "";
            if (p != null) {
                dayTasks += p.getIndex() + "." + p.getName() + "\nHora de inicio:" + p.getStartTime()
                        + "Hora de finalizacion:" + p.getEndTime();
                if (p.getNext() != null) {
                    dayTasks += "  ";
                }
                dayTasks += recursiveExportAux(p.getNext());
            }
            return dayTasks;
        }

        private void printR(Node p) {
            if (p != null) {
                System.out.print(" " + p.getIndex() + "." + p.getName() + " - Hora:" + p.getStartTime()
                        + "-" + p.getEndTime());
                printR(p.getNext());
            }
        }

        class Node {
            private String name;
            private int startTime;
            private int endTime;
            private int index;
            private Node next;

            public Node(String name, int startTime, int endTime, int index) {
                this.name = name;
                this.startTime = startTime;
                this.endTime = endTime;
                this.index = index;
                next = null;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getStartTime() {
                return startTime;
            }

            public void setStartTime(int startTime) {
                this.startTime = startTime;
            }

            public int getEndTime() {
                return endTime;
            }

            public void setEndTime(int endTime) {
                this.endTime = endTime;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public Node getNext() {
                return next;
            }

            public void setNext(Node next) {
                this.next = next;
            }

        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        int maxSize = 10000;
        int increment = 1000;
        PrintWriter out = new PrintWriter("C:\\Users\\ksno\\Desktop\\Programacion\\Java\\output.txt");

        // Schedule schedule = new Schedule();
        // for (int i = 0; i < 10001; i++) {
        //     schedule.newClase(4, "Matematicas", 1, 3);
        // }

        // int k = 4000;
        // int j = 4000;
        // long startTime = System.nanoTime();
        // for (int i = 1; i < k; i++) {
        //     schedule.deleteClase(4, j--);
        // }

        // long endTime = System.nanoTime();
        // long elapsedTime = endTime - startTime;
        // out.println("Size: " + k + ", Time: " + elapsedTime + " nanoseconds");
        // out.close();

        for (int size = 1000; size <= maxSize; size += increment) {
        Schedule schedule = new Schedule();
        Random rand = new Random();
        long startTime = System.nanoTime();
        for (int i = 0; i < size; i++) {
        schedule.newClase(rand.nextInt(7), "Matematicas", 1, 3);
        }
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        out.println("Size: " + size + ", Time: " + elapsedTime + " nanoseconds");
        }
        out.close();
    }
}
