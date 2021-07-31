import adapter.NavigableView;
import adapter.Navigator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NavigatorTest {

    @Test
    public void navigate_to_particular_view() {
        FakeViewA fakeViewA = new FakeViewA();
        FakeViewB fakeViewB = new FakeViewB();

        FakeNavigator navigator = new FakeNavigator();

        navigator.navigateTo(fakeViewA.getClass(), null);
        assertEquals(navigator.zlassName, "FakeViewA");

        navigator.navigateTo(fakeViewB.getClass(), null);
        assertEquals(navigator.zlassName, "FakeViewB");

    }

    private static class FakeViewA implements NavigableView {

    }

    private static class FakeViewB implements NavigableView {

    }

    private static class FakeNavigator implements Navigator {
        String zlassName = "";
        @Override
        public <T extends NavigableView> void navigateTo(Class<T> viewClass, Object model) {
            zlassName = viewClass.getSimpleName();
        }
    }
}