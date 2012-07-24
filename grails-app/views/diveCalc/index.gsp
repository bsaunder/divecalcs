<%--
  Created by IntelliJ IDEA.
  User: Bryan
  Date: May 21, 2009
  Time: 8:43:44 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Dive Calculator</title>
		<meta name="layout" content="main" />
    </head>
    <body>
        <h1 style="margin-left:20px;">Dive Calculator</h1>
        <p style="margin-left:20px;width:400px">Enter as much information about your dives as you can in the form below. The calculator will attempt tom compute any values that are left blank.</p><br />
    <g:hasErrors>
        <table class="errors" width="400">
          <tr><td><b>System Errors</b></td></tr>
          <tr><td><g:renderErrors bean="${cmd}" as="list" /></td></tr>
         </table><br/>
        </g:hasErrors>
        <g:if test="${messages}">
        <table class="errors" width="400">
          <tr><td><b>System Messages</b></td></tr>
          <tr><td><ul><g:each in="${messages}" var="message">
          <li>${message}</li>
          </g:each></ul></td></tr>
         </table><br/>
        </g:if>
        <g:if test="${warnings}">
        <table class="warnings" width="400">
          <tr><td><b>System Warnings</b></td></tr>
          <tr><td><ul><g:each in="${warnings}" var="warning">
          <li>${warning}</li>
          </g:each></ul></td></tr>
         </table><br/>
        </g:if>
        <g:if test="${dives}">
          <table style="margin-left:20px;" width="400">
            <tr>
              <td><b>#</b></td>
              <td><b>SI</b></td>
              <td><b>S. Group</b></td>
              <td><b>Depth</b></td>
              <td><b>ABT</b></td>
              <td><b>RNT</b></td>
              <td><b>TBT</b></td>
              <td><b>E. Group</b></td>
            </tr>
            <g:each in="${dives}" var="dive">
                <g:if test="${dive.value.DP > 0}"><tr>
                  <td>${dive.key}</td>
                  <td>${dive.value.SI}</td>
                  <td>${dive.value.SG}</td>
                  <td>${dive.value.DP}</td>
                  <td>${dive.value.ABT}</td>
                  <td>${dive.value.RNT}</td>
                  <td>${dive.value.TBT}</td>
                  <td><g:if test="${dive.value.EG == 'ERR'}"><font color="#CC0000"><b>ERROR</b></font></g:if><g:else>${dive.value.EG}</g:else>
                    <g:if test="${dive.value.DECO == true}"><font color="#CC0000"><b> (DS)</b></font></g:if></td>
                </tr></g:if>
            </g:each>
            <tr/>
            <tr>
              <td colspan="7"><i>Dives Computed using PADI Table: ${params.table}</i></td>
            </tr>
            <tr><td colspan="7"><font color="#CC0000"><b>DS</b></font> = Safety Stop Required for Dive</td></tr>
          </table>
        </g:if>
    <br/>
        <g:form name="myForm" action="calcDives">
          <table style="margin-left:20px;" width="400">
            <tr>
              <td width=75>Table:</td>
              <td width=325><g:select name="table" from="${['Air (Nitrox 21%)','Nitrox 32%','Nitrox 36%']}" /></td>
            </tr>
            <tr/>
            <tr>
              <td><b>Dive 1</b></td>
              <td>Cold Water: <g:checkBox name="dive1_cold" value="${false}" /></td>
            </tr>
            <tr>
              <td>Depth (ft):</td>
              <td><g:textField name="dive1_depth"/></td>
            </tr>
            <tr>
              <td>Time (min):</td>
              <td><g:textField name="dive1_time"/></td>
            </tr>
            <tr/>
            <tr>
              <td><b>Dive 2</b></td>
              <td>Cold Water: <g:checkBox name="dive2_cold" value="${false}" /></td>
            </tr>
            <tr>
              <td>Surface Int. (min):</td>
              <td><g:textField name="dive2_si"/></td>
            </tr>
            <tr>
              <td>Depth (ft):</td>
              <td><g:textField name="dive2_depth"/></td>
            </tr>
            <tr>
              <td>Time (min):</td>
              <td><g:textField name="dive2_time"/></td>
            </tr>
            <tr>
              <td><b>Dive 3</b></td>
              <td>Cold Water: <g:checkBox name="dive3_cold" value="${false}" /></td>
            </tr>
            <tr>
              <td>Surface Int. (min):</td>
              <td><g:textField name="dive3_si"/></td>
            </tr>
            <tr>
              <td>Depth (ft):</td>
              <td><g:textField name="dive3_depth"/></td>
            </tr>
            <tr>
              <td>Time (min):</td>
              <td><g:textField name="dive3_time"/></td>
            </tr>
            <tr>
              <td><b>Dive 4</b></td>
              <td>Cold Water: <g:checkBox name="dive4_cold" value="${false}" /></td>
            </tr>
            <tr>
              <td>Surface Int. (min):</td>
              <td><g:textField name="dive4_si"/></td>
            </tr>
            <tr>
              <td>Depth (ft):</td>
              <td><g:textField name="dive4_depth"/></td>
            </tr>
            <tr>
              <td>Time (min):</td>
              <td><g:textField name="dive4_time"/></td>
            </tr>
            <tr>
              <td><b>Dive 5</b></td>
              <td>Cold Water: <g:checkBox name="dive5_cold" value="${false}" /></td>
            </tr>
            <tr>
              <td>Surface Int. (min):</td>
              <td><g:textField name="dive5_si"/></td>
            </tr>
            <tr>
              <td>Depth (ft):</td>
              <td><g:textField name="dive5_depth"/></td>
            </tr>
            <tr>
              <td>Time (min):</td>
              <td><g:textField name="dive5_time"/></td>
            </tr>
            <tr>
              <td/>
              <td><g:submitButton name="submit" value="Calculate" /></td>
            </tr>
          </table>
        </g:form>
	<br />
	<table style="margin-left:20px;" width="400">
	        <tr><td><h2>DISCLAIMER</h2></td><tr>
		<tr><td>
			<p>This tool is <b>NOT</b> intended as your sole source of no-decompression dive times. This tool is only intended as a guide to assist in planning your dives. <b>ALWAYS</b> calculate and verify your dive times by hand once you have planned for your dives. If you have a dive computer, follow its instructions for use. The accuracy and reliability of the data provided by this tool is <b>NOT</b> guarenteed by the author.</p>
			<br/><p>SCUBA diving involves serious risks of injury or death, and should only be performed by individuals in good health and with the appropriate skills and training. Even when tables are used with proper safety procedures, decompression sickness may still occur.</p>
			<br/><p>The author provides <b>ABSOLUTELY NO WARRANTY</b> on this calculator, without even the implied warranty of merchantability or fitness for a particular purpose. Use entirely at your own risk. </p>
            <br/><p>Please <a href="http://www.scubadiverinfo.com/2_divetables.html">Click Here</a> for more information on how to use a Dive Table properly.</p>
    </td></tr>
        </table>
        <br/><p style="margin-left:20px;width:400px">This script was last updated on 5/23/2009</p>
        <p style="margin-left:20px;width:400px">Please post any Feedback in this <a href="http://www.scubaboard.com/forums/basic-scuba-discussions/286002-padi-dive-calculator.html">Thread</a></p>
        <p style="margin-left:20px;width:400px">You can contact the Author directly by Private Message <a href="http://www.scubaboard.com/forums/members/orionnt.html">Here</a></p>
   <br/> </body>
</html>