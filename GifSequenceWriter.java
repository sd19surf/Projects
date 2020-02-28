// 
//  GifSequenceWriter.java
//  
//  Created by Elliot Kroo on 2009-04-25.
//
// This work is licensed under the Creative Commons Attribution 3.0 Unported
// License. To view a copy of this license, visit
// http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
// Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.


// Refactored by John Delaney to use in ColdFusion CustomTags on 2017-06-12
// Main was relocated into the processRequest method to return to ColdFusion Web Page
// added the delay and loop controls to give the coder a better UI both variables are 
// optional and default to 100ms for delay and true to loop


import javax.imageio.*;
import javax.imageio.metadata.*;
import javax.imageio.stream.*;
import java.awt.image.*;
import java.io.*;
import java.util.Iterator;
import com.allaire.cfx.*;

public class GifSequenceWriter implements CustomTag {
  protected ImageWriter gifWriter;
  protected ImageWriteParam imageWriteParam;
  protected IIOMetadata imageMetaData;
  
  /**
   * Creates a new GifSequenceWriter
   * 
   * @param outputStream the ImageOutputStream to be written to
   * @param imageType one of the imageTypes specified in BufferedImage
   * @param timeBetweenFramesMS the time between frames in miliseconds
   * @param loopContinuously wether the gif should loop repeatedly
   * @throws IIOException if no gif ImageWriters are found
   *
   * @author Elliot Kroo (elliot[at]kroo[dot]net)
   */
   
   

   
  public GifSequenceWriter() {}
   
  public GifSequenceWriter(
      ImageOutputStream outputStream,
      int imageType,
      int timeBetweenFramesMS,
      boolean loopContinuously) throws IIOException, IOException {
    // my method to create a writer
    gifWriter = getWriter(); 
    imageWriteParam = gifWriter.getDefaultWriteParam();
    ImageTypeSpecifier imageTypeSpecifier =
      ImageTypeSpecifier.createFromBufferedImageType(imageType);

    imageMetaData =
      gifWriter.getDefaultImageMetadata(imageTypeSpecifier,
      imageWriteParam);

    String metaFormatName = imageMetaData.getNativeMetadataFormatName();

    IIOMetadataNode root = (IIOMetadataNode)
      imageMetaData.getAsTree(metaFormatName);

    IIOMetadataNode graphicsControlExtensionNode = getNode(
      root,
      "GraphicControlExtension");

    graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
    graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
    graphicsControlExtensionNode.setAttribute(
      "transparentColorFlag",
      "FALSE");
    graphicsControlExtensionNode.setAttribute(
      "delayTime",
      Integer.toString(timeBetweenFramesMS / 10));
    graphicsControlExtensionNode.setAttribute(
      "transparentColorIndex",
      "0");

    IIOMetadataNode commentsNode = getNode(root, "CommentExtensions");
    commentsNode.setAttribute("CommentExtension", "Created by MAH");

    IIOMetadataNode appEntensionsNode = getNode(
      root,
      "ApplicationExtensions");

    IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");

    child.setAttribute("applicationID", "NETSCAPE");
    child.setAttribute("authenticationCode", "2.0");

    int loop = loopContinuously ? 0 : 1;

    child.setUserObject(new byte[]{ 0x1, (byte) (loop & 0xFF), (byte)
      ((loop >> 8) & 0xFF)});
    appEntensionsNode.appendChild(child);

    imageMetaData.setFromTree(metaFormatName, root);

    gifWriter.setOutput(outputStream);

    gifWriter.prepareWriteSequence(null);
  }
  
  public void writeToSequence(RenderedImage img) throws IOException {
    gifWriter.writeToSequence(
      new IIOImage(
        img,
        null,
        imageMetaData),
      imageWriteParam);
  }
  
  /**
   * Close this GifSequenceWriter object. This does not close the underlying
   * stream, just finishes off the GIF.
   */
  public void close() throws IOException {
    gifWriter.endWriteSequence();    
  }

  /**
   * Returns the first available GIF ImageWriter using 
   * ImageIO.getImageWritersBySuffix("gif").
   * 
   * @return a GIF ImageWriter object
   * @throws IIOException if no GIF image writers are returned
   */
  private static ImageWriter getWriter() throws IIOException {
    Iterator<ImageWriter> iter = ImageIO.getImageWritersBySuffix("gif");
    if(!iter.hasNext()) {
      throw new IIOException("No GIF Image Writers Exist");
    } else {
      return iter.next();
    }
  }

  /**
   * Returns an existing child node, or creates and returns a new child node (if 
   * the requested node does not exist).
   * 
   * @param rootNode the <tt>IIOMetadataNode</tt> to search for the child node.
   * @param nodeName the name of the child node.
   * 
   * @return the child node, if found or a new node created with the given name.
   */
  private static IIOMetadataNode getNode(
      IIOMetadataNode rootNode,
      String nodeName) {
    int nNodes = rootNode.getLength();
    for (int i = 0; i < nNodes; i++) {
      if (rootNode.item(i).getNodeName().compareToIgnoreCase(nodeName)
          == 0) {
        return((IIOMetadataNode) rootNode.item(i));
      }
    }
    IIOMetadataNode node = new IIOMetadataNode(nodeName);
    rootNode.appendChild(node);
    return(node);
  }
  
  /**
  public GifSequenceWriter(
       BufferedOutputStream outputStream,
       int imageType,
       int timeBetweenFramesMS,
       boolean loopContinuously) {
   
   */
   
     /**
   * processRequest is part of the CFX.jar and handles the arguments passed from
   * the tag.  
   * 
   * @param the <tt>Request</tt> gathers the following parameters files, output, delay, and loop
   * @param <tt>Files</tt> is a list seperated by commas
   * @param <tt>Output</tt> is the outbound file usually .gif
   * @param <tt>Delay</tt> is optional and defaults to 100ms
   * @param <tt>Loop</tt> is optional defaults to true and false plays the gif image one time on load.
   * 
   * this is a void method and the output is the outbound created file.
   */
   
     public void processRequest( Request request, Response response )throws Exception { 
  
            //need to get the arguments from the tag and pass them for procesing
     String[] attribs = request.getAttributeList() ;  
     
     // might have to use two names here one list of files and the other output name
     // getAttributesList divides by the files="some.gif,some2.gif,some3.gif" output="test.gif"
     // need to retrieve the list of images as an array
     
    int nNumAttribs = attribs.length ; 
    String[] args = {};
    String strName = "";
    String strValue = "";
    String outFile = "";
    int delay = 100;
    boolean loop = true;
    
    if (attribs.length > 1) {
         for( int i = 0; i < nNumAttribs; i++ ) 
         { 
            strName = attribs[i] ; 
            
            
            
         
       if (strName.equals("FILES")) {
            strValue = request.getAttribute( strName ) ; 
            args = strValue.split(",");
       }
         if (strName.equals("OUTPUT")) {
            strValue = request.getAttribute( strName ) ; 
            outFile = strValue;         
       }
         if (strName.equals("DELAY")) {
            strValue = request.getAttribute( strName ) ;
            try {
               delay = Integer.parseInt(strValue);
            } catch (NumberFormatException e) {
               delay = 100;
            } 
                   
       }
         if (strName.equals("LOOP")) {
            strValue = request.getAttribute( strName ) ; 
            loop = Boolean.parseBoolean(strName);         
       }
       
         }

    } 
       
    if (args.length > 0) {
      // grab the output image type from the first image in the sequence
      BufferedImage firstImage = ImageIO.read(new File(args[0]));

      // create a new BufferedOutputStream with the last argument
      ImageOutputStream output = 
        new FileImageOutputStream(new File(outFile));
      
      // create a gif sequence with the type of the first image, 1 second
      // between frames, which loops continuously
      GifSequenceWriter writer = 
        new GifSequenceWriter(output, firstImage.getType(), delay, loop);
      
      // write out the first image to our sequence...
      writer.writeToSequence(firstImage);
      for(int i=1; i<args.length; i++) {
        BufferedImage nextImage = ImageIO.read(new File(args[i]));
        writer.writeToSequence(nextImage);
      }
      
      writer.close();
      output.close();
    } else {
      response.write( "Usage: cfx_AnimatedGIF file=[list of gif/jpg/png files] output=file.gif delay=#(in ms) loop=true|false");
      
    }
   }  
  
  }