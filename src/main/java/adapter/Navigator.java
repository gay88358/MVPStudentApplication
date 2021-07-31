package adapter;

public interface Navigator {
    <T extends NavigableView> void navigateTo(Class<T> viewClass, Object model);
}
