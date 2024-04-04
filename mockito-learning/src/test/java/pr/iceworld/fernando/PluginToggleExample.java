package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.plugins.PluginSwitch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PluginToggleExample {

    @Mock
    private ValuePlugin valuePlugin;

    @Test
    void PluginToggleExample() {
        // Initialize the plugin switch
        PluginSwitch pluginSwitch = mock(PluginSwitch.class);
        //
        String pluginClzName = ValuePlugin.class.getName();
        when(pluginSwitch.isEnabled(pluginClzName)).thenReturn(true);

        // Toggle the plugin behavior using the switch
        when(valuePlugin.getValue()).thenAnswer(
                invocation -> pluginSwitch.isEnabled(pluginClzName) ? 1 : 2);

        // Create the plugin toggle example

        // Verify initial behavior with plugin switch enabled
        assertEquals(1, valuePlugin.getValue());

        // Disable the plugin switch
        when(pluginSwitch.isEnabled(pluginClzName)).thenReturn(false);

        // Verify behavior after disabling the plugin switch
        assertEquals(2, valuePlugin.getValue());

        // Enable the plugin switch again
        when(pluginSwitch.isEnabled(pluginClzName)).thenReturn(true);

        // Verify behavior after enabling the plugin switch again
        assertEquals(1, valuePlugin.getValue());
    }
}