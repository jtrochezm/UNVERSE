

import java.util.Scanner;

class Subtasks {
    Scanner scanner = new Scanner(System.in);
    int taskIndex;
    AVLSubTask subtasks;
    int currentIntIndex;

    public Subtasks(int taskIndex) {
        this.taskIndex = taskIndex;
        this.currentIntIndex = 1;
        subtasks = new AVLSubTask();
    }

    int UniqueIndexGenerator() {
        return currentIntIndex++;
    }

    int UniqueIndexGenerator(int index) {
        if (index > currentIntIndex) {
            return currentIntIndex++;
        } else if (index < 0) {
            throw new IllegalArgumentException("Por favor, introduzca un número de índice válido.");
        } else if (index == 0) {
            return 1;
        } else {
            currentIntIndex++;
            return index + 1;
        }
    }

    public void createNewSubTask() {
        int option;
        int index;
        System.out.println("\nIngrese la descripcion de la subtarea: ");
        String data = scanner.nextLine();
        System.out.println("\n1. Insertar la tarea al final de la lista");
        System.out.println("2. Insertar la tarea despues de un index de la lista");
        System.out.print("\nIngrese su opción: ");
        option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                newSubTask(data);
                break;
            case 2:
                System.out.println(
                        "\nIngrese el index anterior a la actividad a insertar (para poner la actividad en el primer index digite 0): ");
                index = scanner.nextInt();
                scanner.nextLine();
                newSubTaskWithndex(data, index);
                break;
            default:
                System.out.println("\nOpción no válida. Actividad no insertada.");
        }
    }

    public void createUpdateSubTask() {
        System.out.println("\nIngrese el índice de la subtarea: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        System.out.println("\nIngrese el nuevo dato: ");
        String newData = scanner.nextLine();
        updateSubTask(index, newData);
    }

    public void createDeleteSubTask() {
        System.out.println("\nIngrese el indice de la subtarea: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        deleteSubTask(index);
    }

    public void createSearchSubTask() {
        System.out.println("\nIngrese el indice de la subtarea: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        searchSubTask(index);
    }

    public void searchSubTask(int index) {
        String data = subtasks.searchByIndex(index);
        if (data != null) {
            System.out.println("\nData del nodo con índice " + index + ": " + data);
        } else {
            System.out.println("\nNo se encontró ningún nodo con el índice " + index);
        }
    }

    public void deleteSubTask(int index) {
        if (subtasks.delete(index)) {
            currentIntIndex--;
            System.out.println("\nSubtarea eliminada.");
        } else {
            System.out.println("\nSubtarea no eliminada.");
        }
    }

    public void updateSubTask(int index, String newData) {
        if (subtasks.update(index, newData)) {
            System.out.println("\nSubtarea actualizada.");
        } else {
            System.out.println("\nSubtarea no actualizada.");
        }
    }

    public void newSubTask(String data) {
        if (subtasks.insert(UniqueIndexGenerator(), data)) {
            System.out.println("\nActividad insertada.");
        } else {
            System.out.println("\nActividad no insertada.");
        }

    }

    public void newSubTaskWithndex(String data, int index) {
        if (subtasks.insertWithIndex(UniqueIndexGenerator(index), data)) {
            System.out.println("\nActividad insertada.");
        } else {
            System.out.println("\nActividad no insertada.");
        }
    }

    public void showSubTasks() {
        System.out.println("\nSubtareas:\n");
        subtasks.printTree();
    }

    public void showMenu() {
        int option;
        System.out.println("\nMenú:");
        System.out.println("1. Nueva subtarea");
        System.out.println("2. Actualizar subtarea");
        System.out.println("3. Eliminar subtarea");
        System.out.println("4. Buscar subtarea");
        System.out.println("5. Mostrar subtareas");
        System.out.println("6. Salir");
        do {
            System.out.print("\nIngrese su opción: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    createNewSubTask();
                    break;
                case 2:
                    createUpdateSubTask();
                    break;
                case 3:
                    createDeleteSubTask();
                    break;
                case 4:
                    createSearchSubTask();
                    break;
                case 5:
                    showSubTasks();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("\nOpción no válida. Inténtelo de nuevo.");
            }

        } while (option != 6);

    }

    class AVLSubTask {
        Node root;

        class Node {
            int key, height;
            Node left, right;
            String data;

            Node(int key, String data) {
                this.key = key;
                this.height = 1;
                this.data = data;
            }
        }

        int height(Node node) {
            if (node == null)
                return 0;
            return node.height;
        }

        void updateHeight(Node node) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }

        int getBalance(Node node) {
            if (node == null)
                return 0;
            return height(node.left) - height(node.right);
        }

        Node rightRotate(Node p) {
            Node p1 = p.left;
            Node p2 = p1.right;

            p1.right = p;
            p.left = p2;

            updateHeight(p);
            updateHeight(p1);

            return p1;
        }

        Node leftRotate(Node p) {
            Node p1 = p.right;
            Node p2 = p1.left;

            p1.left = p;
            p.right = p2;

            updateHeight(p);
            updateHeight(p1);

            return p1;
        }

        Node insert(Node node, int key, String data) {
            if (node == null)
                return new Node(key, data);

            if (key < node.key)
                node.left = insert(node.left, key, data);
            else if (key > node.key)
                node.right = insert(node.right, key, data);
            else
                return node;

            updateHeight(node);

            int balance = getBalance(node);

            if (balance > 1) {
                if (key < node.left.key)
                    return rightRotate(node);
                else {
                    node.left = leftRotate(node.left);
                    return rightRotate(node);
                }
            }

            if (balance < -1) {
                if (key > node.right.key)
                    return leftRotate(node);
                else {
                    node.right = rightRotate(node.right);
                    return leftRotate(node);
                }
            }

            return node;
        }

        Node delete(Node node, int key) {
            if (node == null)
                return node;

            if (key < node.key)
                node.left = delete(node.left, key);
            else if (key > node.key)
                node.right = delete(node.right, key);
            else {
                if (node.left == null || node.right == null) {
                    Node temp = (node.left != null) ? node.left : node.right;
                    if (temp == null) {
                        temp = node;
                        node = null;
                    } else
                        node = temp;
                } else {
                    Node temp = minValueNode(node.right);
                    node.key = temp.key;
                    node.right = delete(node.right, temp.key);
                }
            }

            if (node == null)
                return node;

            updateHeight(node);

            int balance = getBalance(node);

            if (balance > 1) {
                if (getBalance(node.left) >= 0)
                    return rightRotate(node);
                else {
                    node.left = leftRotate(node.left);
                    return rightRotate(node);
                }
            }

            if (balance < -1) {
                if (getBalance(node.right) <= 0)
                    return leftRotate(node);
                else {
                    node.right = rightRotate(node.right);
                    return leftRotate(node);
                }
            }

            return node;
        }

        Node minValueNode(Node node) {
            Node current = node;
            while (current.left != null)
                current = current.left;
            return current;
        }

        void inOrderTraversal(Node node) {
            if (node != null) {
                inOrderTraversal(node.left);
                System.out.print(node.key + "." + node.data + " ");
                inOrderTraversal(node.right);
            }
        }

        boolean insert(int key, String data) {
            root = insert(root, key, data);
            if (root instanceof Node) {
                return true;
            } else {
                return false;
            }
        }

        boolean update(int index, String newData) {
            root = update(root, index, newData);
            if (root instanceof Node) {
                return true;
            } else {
                return false;
            }
        }

        Node update(Node node, int index, String newData) {
            if (node == null) {
                return node;
            }

            if (index < node.key) {
                node.left = update(node.left, index, newData);
            } else if (index > node.key) {
                node.right = update(node.right, index, newData);
            } else {
                node.data = newData;
            }

            return node;
        }

        String searchByIndex(int index) {
            return searchByIndex(root, index);
        }

        String searchByIndex(Node node, int index) {
            if (node == null) {
                return null;
            }

            if (index < node.key) {
                return searchByIndex(node.left, index);
            } else if (index > node.key) {
                return searchByIndex(node.right, index);
            } else {
                return node.data;
            }
        }

        boolean delete(int key) {
            root = delete(root, key);
            decrementNodeKeys(key);
            if (root instanceof Node) {
                return true;
            } else {
                return false;
            }
        }

        void printTree() {
            inOrderTraversal(root);
            System.out.println();
        }

        String getData(int key) {
            return getData(root, key);
        }

        String getData(Node node, int key) {
            if (node == null) {
                return null;
            }

            if (key < node.key) {
                return getData(node.left, key);
            } else if (key > node.key) {
                return getData(node.right, key);
            } else {
                return node.data;
            }
        }

        boolean insertWithIndex(int index, String data) {
            incrementNodeKeys(index);
            if (insert(index, data)) {
                return true;
            } else {
                return false;
            }
        }

        public void incrementNodeKeys(int index) {
            incrementNodeKeys(root, index);
        }

        private void incrementNodeKeys(Node node, int index) {
            if (node == null) {
                return;
            }

            incrementNodeKeys(node.left, index);

            if (node.key >= index) {
                node.key++;
            }

            incrementNodeKeys(node.right, index);
        }

        public void decrementNodeKeys(int index) {
            decrementNodeKeys(root, index);
        }

        private void decrementNodeKeys(Node node, int index) {
            if (node == null) {
                return;
            }

            decrementNodeKeys(node.left, index);

            if (node.key >= index) {
                node.key--;
            }

            decrementNodeKeys(node.right, index);
        }

    }

    public static void main(String[] args) {
        Subtasks sb = new Subtasks(1);
        //time tests (CRUD based):
        //function to add subtasks newSubTask(String subtask)
        //function to add subtasks after an index newSubTaskWithndex(String data, int index)
        //function to delete subtasks deleteSubTask(int index)
        //function to search a subtask searchSubTask(int index)
        //function to update a subtask updateSubTask(int index, String newData)
    }
}
