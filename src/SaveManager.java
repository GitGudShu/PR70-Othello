import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SaveManager {

    private static final String SAVE_DIRECTORY = "saves";

    // Save the game state to an XML file
    public static void saveGame(GameStatus gameState, String fileName) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("game");
            doc.appendChild(rootElement);

            // Save file name
            Element saveName = doc.createElement("saveName");
            saveName.appendChild(doc.createTextNode(gameState.getSaveName()));
            rootElement.appendChild(saveName);

            // Who's turn is it
            Element currentPlayer = doc.createElement("currentPlayer");
            currentPlayer.appendChild(doc.createTextNode(String.valueOf(gameState.getCurrentPlayer())));
            rootElement.appendChild(currentPlayer);

            // Matrix
            Element gridElement = doc.createElement("grid");
            for (int[] row : gameState.getGrid()) {
                Element rowElement = doc.createElement("row");
                rowElement.appendChild(doc.createTextNode(arrayToString(row)));
                gridElement.appendChild(rowElement);
            }
            rootElement.appendChild(gridElement);

            // Score data
            Element playerScore = doc.createElement("playerScore");
            Element whiteScore = doc.createElement("white");
            whiteScore.appendChild(doc.createTextNode(String.valueOf(gameState.getWhiteScore())));
            playerScore.appendChild(whiteScore);

            Element blackScore = doc.createElement("black");
            blackScore.appendChild(doc.createTextNode(String.valueOf(gameState.getBlackScore())));
            playerScore.appendChild(blackScore);
            rootElement.appendChild(playerScore);

            // Write the content to an XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            String t_fileName = SAVE_DIRECTORY + "/" + fileName;
            StreamResult result = new StreamResult(new File(t_fileName));

            transformer.transform(source, result);
            System.out.println("Game saved successfully to " + fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GameStatus loadGame(String fileName) {
        try {
            String t_fileName = SAVE_DIRECTORY + "/" + fileName;
            File xmlFile = new File(t_fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Extract data from the XML
            String saveName = doc.getElementsByTagName("saveName").item(0).getTextContent();
            int currentPlayer = Integer.parseInt(doc.getElementsByTagName("currentPlayer").item(0).getTextContent());

            NodeList rows = doc.getElementsByTagName("row");
            int[][] grid = new int[8][8];
            for (int i = 0; i < rows.getLength(); i++) {
                String[] rowValues = rows.item(i).getTextContent().split(" ");
                for (int j = 0; j < rowValues.length; j++) {
                    grid[i][j] = Integer.parseInt(rowValues[j]);
                }
            }

            int whiteScore = Integer.parseInt(doc.getElementsByTagName("white").item(0).getTextContent());
            int blackScore = Integer.parseInt(doc.getElementsByTagName("black").item(0).getTextContent());

            return new GameStatus(saveName, currentPlayer, grid, whiteScore, blackScore);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Convert the xml matrix to a string
    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int value : array) {
            sb.append(value).append(" ");
        }
        return sb.toString().trim();
    }

    public static List<String> getAvailableSaves() {

        List<String> saveList = new ArrayList<>();
        File saveDir = new File(SAVE_DIRECTORY);
        File[] files = saveDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));

        if (files != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (File file : files) {
                String fileName = file.getName();
                String lastModified = sdf.format(file.lastModified());
                saveList.add(fileName + " (Saved on: " + lastModified + ")");
            }
        }
        return saveList;
    }
}
