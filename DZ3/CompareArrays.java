/**   2.Напишите обобщенный метод compareArrays(), который принимает два массива и возвращает true,
 если они одинаковые, и false в противном случае. Массивы могут быть любого типа данных, но должны иметь одинаковую длину и
 содержать элементы одного типа по парно по индексам. */

public class CompareArrays {

    public static <T> boolean compareArrays(T[] arr1, T[] arr2) {
        if (arr1 == arr2) {
            return true;
        }
        if (arr1 == null || arr2 == null) {
            return false;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (!arr1[i].equals(arr2[i])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Integer[] arr1 = {1, 2, 3};
        Integer[] arr2 = {1, 2, 3};
        String[] arr3 = {"1", "2", "3"};
        String[] arr4 = {"1", "2", "3"};
        String[] arr5 = {"3", "2", "1"};

        System.out.println(compareArrays(arr1, arr2));
        System.out.println(compareArrays(arr3, arr4));
        System.out.println(compareArrays(arr3, arr5));
    }
}

